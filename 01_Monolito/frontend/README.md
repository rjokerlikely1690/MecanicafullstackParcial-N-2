# AutoMax Frontend

Frontend en React para el sistema de gestión de taller mecánico AutoMax. Este repositorio contiene la interfaz web, la navegación por roles y el consumo de la API del backend.

## Alcance del proyecto

- Landing pública con acceso a login y registro.
- Autenticación con persistencia de sesión en `localStorage`.
- Rutas protegidas para administradores y clientes.
- Gestión de servicios, turnos, vehículos, repuestos y mensajes desde la interfaz.
- Dashboard para administración y vistas específicas por rol.

## Tecnologías utilizadas

- React 19
- React Router DOM
- Axios
- React-Bootstrap
- Bootstrap 5

## Estructura principal

```text
src/
├── components/
│   ├── auth/              # Login y registro
│   ├── dashboard/         # Vista principal por rol
│   ├── landing/           # Página pública
│   ├── agenda/            # Gestión de turnos
│   ├── servicios/         # Catálogo y administración de servicios
│   ├── vehiculos/         # Gestión de vehículos
│   ├── repuestos/         # Catálogo de repuestos
│   ├── mensajes/          # Mensajería interna
│   ├── reserva/           # Reserva de servicios
│   ├── pages/             # Páginas de composición
│   └── ...                # Componentes reutilizables
├── context/
│   └── AuthContext.js     # Estado global de autenticación
├── services/
│   └── apiService.js      # Cliente HTTP y servicios API
├── utils/
│   └── ...                # Utilidades compartidas
└── App.js                 # Rutas principales de la aplicación
```

## Instalación

```bash
npm install
npm start
```

La aplicación se ejecuta en `http://localhost:3000`.

## Scripts disponibles

- `npm start`: inicia el entorno de desarrollo.
- `npm test`: ejecuta las pruebas.
- `npm run test:coverage`: genera cobertura.
- `npm run test:karma`: ejecuta pruebas con Karma.
- `npm run build`: crea la versión de producción.

## Backend requerido

Este frontend consume una API REST externa. Por defecto, el cliente apunta a:

```text
http://localhost:8080/api
```

Si el backend corre en otra dirección, ajusta la URL en `src/services/apiService.js`.

## Flujo funcional

1. El usuario entra al landing.
2. Inicia sesión o crea una cuenta.
3. El frontend guarda token y usuario.
4. Las rutas privadas se habilitan según el rol.
5. El usuario consulta o administra módulos según permisos.

## Notas para entrega

- Este repositorio contiene solo el frontend.
- El backend debe estar disponible por separado para que la aplicación funcione completo.
- Si vas a compartir el proyecto con un profesor, este `README.md` resume lo necesario para instalarlo y entenderlo rápidamente.
