package cl.duocuc.tallermecanico.service;

import cl.duocuc.tallermecanico.dto.AuthResponse;
import cl.duocuc.tallermecanico.dto.LoginRequest;
import cl.duocuc.tallermecanico.dto.RegisterRequest;
import cl.duocuc.tallermecanico.model.Rol;
import cl.duocuc.tallermecanico.model.Usuario;
import cl.duocuc.tallermecanico.repository.UsuarioRepository;
import cl.duocuc.tallermecanico.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private org.springframework.security.core.Authentication lastAuthentication;

    private final JwtUtil jwtUtil = new JwtUtil();

    private AuthService authService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtil, "secret", "01234567890123456789012345678901");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 3600000L);

        authService = new AuthService();
        authenticationManager = authentication -> {
            lastAuthentication = authentication;
            return new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials()
            );
        };
        ReflectionTestUtils.setField(authService, "usuarioRepository", usuarioRepository);
        ReflectionTestUtils.setField(authService, "passwordEncoder", passwordEncoder);
        ReflectionTestUtils.setField(authService, "jwtUtil", jwtUtil);
        ReflectionTestUtils.setField(authService, "authenticationManager", authenticationManager);
    }

    @Test
    void register_creaUsuarioCliente_yDevuelveToken() {
        RegisterRequest request = new RegisterRequest("Juan Perez", "juan@correo.cl", "secreto123");
        AtomicReference<Usuario> savedUsuario = new AtomicReference<>();

        when(usuarioRepository.existsByEmail("juan@correo.cl")).thenReturn(false);
        when(passwordEncoder.encode("secreto123")).thenReturn("hashed-password");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            usuario.setId("usuario-1");
            savedUsuario.set(usuario);
            return usuario;
        });
        when(usuarioRepository.findByEmail("juan@correo.cl")).thenAnswer(invocation -> Optional.of(savedUsuario.get()));

        AuthResponse response = authService.register(request);

        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(usuarioCaptor.capture());
        assertEquals("Juan Perez", usuarioCaptor.getValue().getNombre());
        assertEquals("juan@correo.cl", usuarioCaptor.getValue().getEmail());
        assertEquals("hashed-password", usuarioCaptor.getValue().getPassword());
        assertEquals(Rol.CLIENTE, usuarioCaptor.getValue().getRol());
        assertTrue(usuarioCaptor.getValue().getActivo());

        assertEquals("usuario-1", response.getId());
        assertEquals("Juan Perez", response.getNombre());
        assertEquals("juan@correo.cl", response.getEmail());
        assertEquals(Rol.CLIENTE, response.getRol());
        assertFalse(response.getToken().isBlank());
    }

    @Test
    void register_falla_siElEmailYaExiste() {
        RegisterRequest request = new RegisterRequest("Juan Perez", "juan@correo.cl", "secreto123");

        when(usuarioRepository.existsByEmail("juan@correo.cl")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.register(request));

        assertEquals("El email ya está registrado", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void login_devuelveAuthResponse_paraUsuarioActivo() {
        LoginRequest request = new LoginRequest("juan@correo.cl", "secreto123");
        Usuario usuario = new Usuario();
        usuario.setId("usuario-1");
        usuario.setNombre("Juan Perez");
        usuario.setEmail("juan@correo.cl");
        usuario.setPassword("hashed-password");
        usuario.setRol(Rol.CLIENTE);
        usuario.setActivo(true);

        when(usuarioRepository.findByEmail("juan@correo.cl")).thenReturn(Optional.of(usuario));

        AuthResponse response = authService.login(request);

        assertTrue(lastAuthentication instanceof UsernamePasswordAuthenticationToken);
        UsernamePasswordAuthenticationToken captured = (UsernamePasswordAuthenticationToken) lastAuthentication;
        assertEquals("juan@correo.cl", captured.getPrincipal());
        assertEquals("secreto123", captured.getCredentials());

        assertEquals("usuario-1", response.getId());
        assertEquals("Juan Perez", response.getNombre());
        assertEquals("juan@correo.cl", response.getEmail());
        assertEquals(Rol.CLIENTE, response.getRol());
        assertFalse(response.getToken().isBlank());
    }
}
