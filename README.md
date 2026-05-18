# AutoMax - Evaluación Parcial N° 2

Repositorio final del proyecto **AutoMax**, orientado a la gestión de un taller mecánico. Este repositorio contiene la aplicación ejecutable en monolito y la documentación usada para la entrega académica.

## Contenido del repositorio

- `01_Monolito/backend/`: API REST con Spring Boot, seguridad JWT y persistencia en MongoDB.
- `01_Monolito/frontend/`: interfaz React con rutas protegidas, dashboard y módulos funcionales.
- `03_Documentacion/`: pauta, presentación y material de apoyo de la evaluación.

## Tecnologías principales

- React 19
- React Router DOM
- Axios
- Spring Boot 3.2
- Spring Security
- JWT
- MongoDB Atlas
- Maven

## Ejecución local

### Backend

```bash
cd 01_Monolito/backend
mvn spring-boot:run
```

### Frontend

```bash
cd 01_Monolito/frontend
npm install
npm start
```

## Estructura general

```text
AutoMax/
├── 01_Monolito/
│   ├── backend/
│   └── frontend/
└── 03_Documentacion/
```

## Observaciones

- Este repositorio está preparado para revisión académica.
- La documentación incluida corresponde a la versión final de la entrega.
- El frontend consume la API del backend mediante `http://localhost:8080/api`.
