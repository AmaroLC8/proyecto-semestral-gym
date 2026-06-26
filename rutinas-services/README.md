# 🏋️ Microservicio: rutinas-services

## Contexto o Dominio del Sistema

Este microservicio gestiona el catálogo de **rutinas de entrenamiento** del gimnasio. Permite a los entrenadores y administradores crear, consultar y eliminar rutinas estructuradas que los socios pueden seguir. Cada rutina define el tipo de ejercicio, su nivel de dificultad y la duración estimada.

---

## Integrantes del Equipo

| Nombre | Rol |
|---|---|
| Amaro Lopez | Desarrollador Backend |
| _(Integrante 2)_ | _(Rol)_ |
| _(Integrante 3)_ | _(Rol)_ |

---

## Responsabilidades de este Microservicio

- Crear nuevas rutinas de entrenamiento con nombre, descripción, duración y nivel de dificultad.
- Listar todas las rutinas disponibles en el sistema.
- Obtener el detalle de una rutina por su ID.
- Eliminar rutinas del catálogo.

### Reglas de Negocio implementadas
1. **Duración válida**: La duración debe estar entre 10 y 300 minutos.
2. **Nivel de dificultad válido**: Solo se aceptan `PRINCIPIANTE`, `INTERMEDIO` o `AVANZADO`.
3. **Nombre único**: No pueden existir dos rutinas con el mismo nombre (insensible a mayúsculas).

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Descripción |
|---|---|---|
| `GET` | `/api/rutinas` | Listar todas las rutinas |
| `GET` | `/api/rutinas/{id}` | Obtener una rutina por ID |
| `POST` | `/api/rutinas` | Crear una nueva rutina |
| `DELETE` | `/api/rutinas/{id}` | Eliminar una rutina |

---

## Enlace Swagger

| Entorno | URL |
|---|---|
| Local | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) |
| Docker | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) |

---

## Instrucciones de Ejecución Local

### Pre-requisitos
- Java 17+
- Maven 3.8+
- Base de datos PostgreSQL corriendo en `localhost:5432`

### Pasos
```bash
cd rutinas-services
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8082`

---

## Instrucciones de Despliegue Remoto (Docker)

```bash
docker build -t rutinas-services:latest .

docker run -d \
  --name rutinas-services \
  -p 8082:8082 \
  -e DB_HOST=<host_bd> \
  -e DB_PORT=5432 \
  -e DB_NAME=db_rutinas \
  -e DB_USER=<usuario_bd> \
  -e DB_PASSWORD=<contraseña_bd> \
  rutinas-services:latest

# O con Docker Compose:
docker-compose up rutinas-services
```

---

## Variables de Entorno Requeridas

| Variable | Descripción | Valor de Ejemplo |
|---|---|---|
| `DB_HOST` | Host de la base de datos PostgreSQL | `localhost` / `db-rutinas` |
| `DB_PORT` | Puerto de la base de datos | `5432` |
| `DB_NAME` | Nombre de la base de datos | `db_rutinas` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `secret` |
| `SERVER_PORT` | Puerto en que corre el servicio | `8082` |
