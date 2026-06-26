# 🏋️ Microservicio: reservas-services

## Contexto o Dominio del Sistema

Este microservicio gestiona las **reservas de productos o servicios** del gimnasio (por ejemplo, clases, equipamiento, espacios). Los socios pueden realizar reservas que pasan por estados de ciclo de vida: `PENDIENTE` → `CONFIRMADA` o `CANCELADA`.

---

## Integrantes del Equipo

| Nombre | Rol |
|---|---|
| Amaro Lopez | Desarrollador Backend |
| _(Integrante 2)_ | _(Rol)_ |
| _(Integrante 3)_ | _(Rol)_ |

---

## Responsabilidades de este Microservicio

- Crear nuevas reservas para usuarios del gimnasio.
- Listar todas las reservas existentes.
- Obtener el detalle de una reserva por ID.
- Eliminar reservas del sistema.
- Validar que las fechas de reserva sean futuras.
- Controlar el límite de reservas activas por usuario.

### Reglas de Negocio implementadas
1. **Estado válido**: Solo se aceptan `PENDIENTE`, `CONFIRMADA` o `CANCELADA`.
2. **Fecha futura**: La fecha de reserva no puede ser anterior a la fecha actual.
3. **Límite de reservas**: Un usuario no puede tener más de 5 reservas `PENDIENTE` simultáneamente.

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Descripción |
|---|---|---|
| `GET` | `/api/reservas` | Listar todas las reservas |
| `GET` | `/api/reservas/{id}` | Obtener una reserva por ID |
| `POST` | `/api/reservas` | Crear una nueva reserva |
| `DELETE` | `/api/reservas/{id}` | Eliminar una reserva |

---

## Enlace Swagger

| Entorno | URL |
|---|---|
| Local | [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html) |
| Docker | [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html) |

---

## Instrucciones de Ejecución Local

```bash
cd reservas-services
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8083`

---

## Instrucciones de Despliegue Remoto (Docker)

```bash
docker build -t reservas-services:latest .

docker run -d \
  --name reservas-services \
  -p 8083:8083 \
  -e DB_HOST=<host_bd> \
  -e DB_PORT=5432 \
  -e DB_NAME=db_reservas \
  -e DB_USER=<usuario_bd> \
  -e DB_PASSWORD=<contraseña_bd> \
  reservas-services:latest

# O con Docker Compose:
docker-compose up reservas-services
```

---

## Variables de Entorno Requeridas

| Variable | Descripción | Valor de Ejemplo |
|---|---|---|
| `DB_HOST` | Host de la base de datos PostgreSQL | `localhost` / `db-reservas` |
| `DB_PORT` | Puerto de la base de datos | `5432` |
| `DB_NAME` | Nombre de la base de datos | `db_reservas` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `secret` |
| `SERVER_PORT` | Puerto en que corre el servicio | `8083` |
