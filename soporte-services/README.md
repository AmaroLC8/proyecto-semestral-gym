# 🏋️ Microservicio: soporte-services

## Contexto o Dominio del Sistema

Este microservicio gestiona el **sistema de tickets de soporte técnico y atención al cliente** del gimnasio. Los socios pueden crear tickets con problemas o consultas, los cuales son gestionados por el equipo administrativo hasta su resolución.

---

## Integrantes del Equipo

| Nombre | Rol |
|---|---|
| Amaro Lopez | Desarrollador Backend |
| _(Integrante 2)_ | _(Rol)_ |
| _(Integrante 3)_ | _(Rol)_ |

---

## Responsabilidades de este Microservicio

- Crear tickets de soporte para usuarios del gimnasio.
- Listar todos los tickets del sistema.
- Consultar tickets por usuario o por ID.
- Responder tickets (actualizando estado a `RESUELTO`).
- Controlar la cantidad de tickets activos por usuario.

### Reglas de Negocio implementadas
1. **Límite de tickets**: Un usuario no puede tener más de 3 tickets `PENDIENTE` simultáneamente.
2. **Sin duplicados**: No se pueden crear dos tickets con el mismo asunto y estado `PENDIENTE` para el mismo usuario.
3. **Descripción mínima**: La descripción del ticket debe tener al menos 20 caracteres para garantizar contexto suficiente.

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Descripción |
|---|---|---|
| `GET` | `/api/soporte` | Listar todos los tickets |
| `GET` | `/api/soporte/{id}` | Obtener un ticket por ID |
| `GET` | `/api/soporte/usuario/{usuarioId}` | Tickets de un usuario |
| `POST` | `/api/soporte` | Crear un nuevo ticket |
| `PUT` | `/api/soporte/{id}/responder` | Responder un ticket |

---

## Enlace Swagger

| Entorno | URL |
|---|---|
| Local | [http://localhost:8088/swagger-ui/index.html](http://localhost:8088/swagger-ui/index.html) |
| Docker | [http://localhost:8088/swagger-ui/index.html](http://localhost:8088/swagger-ui/index.html) |

---

## Instrucciones de Ejecución Local

```bash
cd soporte-services
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8088`

---

## Instrucciones de Despliegue Remoto (Docker)

```bash
docker build -t soporte-services:latest .

docker run -d \
  --name soporte-services \
  -p 8088:8088 \
  -e DB_HOST=<host_bd> \
  -e DB_PORT=5432 \
  -e DB_NAME=db_soporte \
  -e DB_USER=<usuario_bd> \
  -e DB_PASSWORD=<contraseña_bd> \
  soporte-services:latest

# O con Docker Compose:
docker-compose up soporte-services
```

---

## Variables de Entorno Requeridas

| Variable | Descripción | Valor de Ejemplo |
|---|---|---|
| `DB_HOST` | Host de la base de datos PostgreSQL | `localhost` / `db-soporte` |
| `DB_PORT` | Puerto de la base de datos | `5432` |
| `DB_NAME` | Nombre de la base de datos | `db_soporte` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `secret` |
| `SERVER_PORT` | Puerto en que corre el servicio | `8088` |
