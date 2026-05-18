package cl.duocuc.tallermecanico.service;

import cl.duocuc.tallermecanico.dto.OrdenRequest;
import cl.duocuc.tallermecanico.model.Orden;
import cl.duocuc.tallermecanico.model.Turno;
import cl.duocuc.tallermecanico.repository.OrdenRepository;
import cl.duocuc.tallermecanico.repository.TurnoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @Mock
    private TurnoRepository turnoRepository;

    private AgendaService agendaService;

    private OrdenService ordenService;

    @BeforeEach
    void setUp() {
        agendaService = new AgendaService(turnoRepository);
        ordenService = new OrdenService(ordenRepository, turnoRepository, agendaService);
    }

    @Test
    void crearOrdenConTurno_generaTurnoConfirmado_yOrdenPagada() {
        OrdenRequest request = new OrdenRequest();
        request.setClienteNombre("Juan Perez");
        request.setClienteEmail("juan@correo.cl");
        request.setServicioNombre("Cambio de aceite");
        request.setPrecio(BigDecimal.valueOf(45000));
        request.setFechaTurno(LocalDate.now().plusDays(1));
        request.setHoraTurno("10:30");
        request.setNotas("Cliente solicita revision completa");
        request.setMetodoPago("TARJETA");

        when(turnoRepository.save(any(Turno.class))).thenAnswer(invocation -> {
            Turno turnoGuardado = invocation.getArgument(0);
            turnoGuardado.setId("turno-123");
            return turnoGuardado;
        });
        when(ordenRepository.save(any(Orden.class))).thenAnswer(invocation -> {
            Orden orden = invocation.getArgument(0);
            orden.setId("orden-1");
            return orden;
        });

        Orden resultado = ordenService.crearOrdenConTurno(request);

        ArgumentCaptor<Turno> turnoCaptor = ArgumentCaptor.forClass(Turno.class);
        verify(turnoRepository).save(turnoCaptor.capture());
        assertEquals(request.getFechaTurno(), turnoCaptor.getValue().getFecha());
        assertEquals(request.getHoraTurno(), turnoCaptor.getValue().getHora());
        assertEquals(request.getServicioNombre(), turnoCaptor.getValue().getServicioSolicitado());
        assertEquals(request.getClienteNombre(), turnoCaptor.getValue().getClienteNombre());
        assertEquals(request.getClienteEmail(), turnoCaptor.getValue().getClienteEmail());
        assertEquals(request.getNotas(), turnoCaptor.getValue().getNotas());
        assertTrue(turnoCaptor.getValue().isConfirmado());

        ArgumentCaptor<Orden> ordenCaptor = ArgumentCaptor.forClass(Orden.class);
        verify(ordenRepository).save(ordenCaptor.capture());
        assertEquals("Juan Perez", ordenCaptor.getValue().getClienteNombre());
        assertEquals("juan@correo.cl", ordenCaptor.getValue().getClienteEmail());
        assertEquals("Cambio de aceite", ordenCaptor.getValue().getServicioNombre());
        assertEquals(BigDecimal.valueOf(45000), ordenCaptor.getValue().getPrecio());
        assertEquals("PAGADO", ordenCaptor.getValue().getEstadoPago());
        assertEquals("TARJETA", ordenCaptor.getValue().getMetodoPago());
        assertEquals("turno-123", ordenCaptor.getValue().getTurnoId());

        assertEquals("orden-1", resultado.getId());
        assertEquals("PAGADO", resultado.getEstadoPago());
        assertEquals("turno-123", resultado.getTurnoId());
        assertNotNull(resultado.getCreadoEn());
    }
}
