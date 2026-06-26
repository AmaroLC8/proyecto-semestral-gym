# 🏋️ Microservicio: seguimientos-services

## Contexto o Dominio del Sistema

Este microservicio gestiona el **seguimiento físico y de progreso** de los socios del gimnasio. Registra métricas corporales como el peso y el porcentaje de grasa para permitir el monitoreo del avance de cada socio a lo largo del tiempo.

---

## Integrantes del Equipo

| Nombre | Rol |
|---|---|
| Amaro Lopez | Desarrollador Backend |
| _(Integrante 2)_ | _(Rol)_ |
| _(Integrante 3)_ | _(Rol)_ |

---

## Responsabilidades de este Microservicio

- Registrar mediciones físicas de socios (peso, porcentaje de grasa).
- Listar todos los registros de seguimiento.
- Obtener un seguimiento específico por ID.
- Eliminar registros de seguimiento.

### Reglas de Negocio implementadas
1. **Rango de peso válido**: El peso debe estar entre 20 kg y 300 kg.
2. **Porcentaje de grasa**: No puede superar el 60% (máximo fisiológico).
3. **Cooldown entre registros**: Un socio no puede registrar un nuevo seguimiento si ya tiene uno en los últimos 3 días.

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Descripción |
|---|---|---|
| `GET` | `/api/seguimientos` | Listar todos los seguimientos |
| `GET` | `/api/seguimientos/{id}` | Obtener un seguimiento por ID |
| `POST` | `/api/seguimientos` | Registrar un nuevo seguimiento |
| `DELETE` | `/api/seguimientos/{id}` | Eliminar un seguimiento |

---

## Enlace Swagger

| Entorno | URL |
|---|---|
| Local | [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html) |
| Docker | [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html) |

---

## Instrucciones de Ejecución Local

```bash
cd seguimientos-services
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8084`

---

## Instrucciones de Despliegue Remoto (Docker)

```bash
docker build -t seguimientos-services:latest .

docker run -d \
  --name seguimientos-services \
  -p 8084:8084 \
  -e DB_HOST=<host_bd> \
  -e DB_PORT=5432 \
  -e DB_NAME=db_seguimientos \
  -e DB_USER=<usuario_bd> \
  -e DB_PASSWORD=<contraseña_bd> \
  seguimientos-services:latest

# O con Docker Compose:
docker-compose up seguimientos-services
```

---

## Variables de Entorno Requeridas

| Variable | Descripción | Valor de Ejemplo |
|---|---|---|
| `DB_HOST` | Host de la base de datos PostgreSQL | `localhost` / `db-seguimientos` |
| `DB_PORT` | Puerto de la base de datos | `5432` |
| `DB_NAME` | Nombre de la base de datos | `db_seguimientos` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `secret` |
| `SERVER_PORT` | Puerto en que corre el servicio | `8084` |
