# AutoMax Monolito

Modulo principal del proyecto AutoMax. Contiene la aplicacion ejecutable utilizada para la entrega academica, separada en backend y frontend.

## Estructura

- `backend/`: API REST con Spring Boot, seguridad JWT y persistencia en MongoDB.
- `frontend/`: Aplicacion React con navegacion por roles, dashboard y modulos funcionales.

## Ejecucion local

### Backend

```bash
cd backend
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm start
```

## Tecnologias principales

- Spring Boot 3.2
- Spring Security
- JWT
- MongoDB Atlas
- React 19
- React Router DOM
- Axios
- React-Bootstrap

## Notas

- El frontend consume la API del backend en `http://localhost:8080/api`.
- No se incluyen dependencias compiladas (`node_modules/`, `target/`) en el repositorio.
