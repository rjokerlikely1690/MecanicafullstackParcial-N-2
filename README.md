# AutoMax - Evaluación Parcial N.° 2

<p align="center">
  <img src="03_Documentacion/Presentacion_Defensa/Arquitectura_AutoMax.png" alt="Arquitectura AutoMax" width="900" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/React-19-61DAFB?style=for-the-badge&logo=react&logoColor=white" alt="React" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/MongoDB-Atlas-47A248?style=for-the-badge&logo=mongodb&logoColor=white" alt="MongoDB" />
  <img src="https://img.shields.io/badge/JWT-Security-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white" alt="JWT" />
</p>

Repositorio final del proyecto **AutoMax** para la **Evaluación Parcial N.° 2**. El repositorio reúne la aplicación ejecutable del taller mecánico, su backend en Spring Boot, el frontend en React y la documentación académica de apoyo.

## Resumen ejecutivo

AutoMax centraliza la gestión de un taller mecánico en una solución web organizada por capas. El proyecto contempla autenticación, administración de vehículos, turnos, servicios, repuestos y mensajes internos, junto con pruebas unitarias y material de presentación.

## Arquitectura general

```text
Cliente web
   ↓
Frontend React
   ↓
API REST Spring Boot
   ↓
MongoDB Atlas
```

La implementación principal es un **monolito full-stack** listo para ejecución local. La documentación adicional del repositorio también incluye material conceptual de microservicios para explicar el diseño de la solución.

## Contenido del repositorio

| Ruta | Descripción |
|---|---|
| `01_Monolito/backend/` | API REST con Spring Boot, seguridad JWT, controladores, servicios, repositorios y pruebas. |
| `01_Monolito/frontend/` | Interfaz en React con rutas protegidas, dashboards y módulos funcionales. |
| `03_Documentacion/` | Presentación, pauta, evidencias y material de soporte de la entrega. |

## Tecnologías principales

| Capa | Tecnologías |
|---|---|
| Frontend | React 19, React Router DOM, Axios, CSS |
| Backend | Spring Boot 3.2, Spring Security, JWT, Maven |
| Persistencia | MongoDB Atlas |
| Calidad | JUnit, Mockito |
| Documentación | PowerPoint, Word, Markdown |

## Funcionalidades destacadas

- Autenticación con JWT y protección de rutas.
- Gestión de vehículos, turnos, servicios y repuestos.
- Paneles diferenciados para administrador y cliente.
- Datos iniciales para facilitar la revisión académica.
- Pruebas unitarias enfocadas en servicios críticos del backend.
- Material de defensa y evidencias integradas en `03_Documentacion/`.

## Estructura general

```text
AutoMax/
├── 01_Monolito/
│   ├── backend/
│   └── frontend/
└── 03_Documentacion/
```

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

El frontend consume la API del backend en `http://localhost:8080/api`.

## Evidencias y documentación

- Presentación: `03_Documentacion/Presentacion_Defensa/Presentacion_Defensa_AutoMax.pptx`
- Guion de defensa: `03_Documentacion/Presentacion_Defensa/Guion_Defensa_AutoMax.md`
- Evidencia visual de pruebas: `03_Documentacion/Presentacion_Defensa/evidencia_tests.png`
- Documento de apoyo académico: `03_Documentacion/Evaluacion_Parcial_2/EVALUACION_PARCIAL_2_ESTUDIANTE.pdf`

## Notas de entrega

- El repositorio está preparado para revisión académica.
- No incluye dependencias compiladas como `node_modules/` ni `target/`.
- La configuración sensible del backend debe resolverse con variables de entorno en despliegue.
