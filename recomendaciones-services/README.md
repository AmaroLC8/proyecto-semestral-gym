# 🏋️ Microservicio: recomendaciones-services

## Contexto o Dominio del Sistema

Este microservicio gestiona las **recomendaciones personalizadas** para los socios del gimnasio. Los entrenadores o el sistema pueden crear recomendaciones de entrenamiento, nutrición y hábitos saludables que se asocian a un socio específico.

---

## Integrantes del Equipo

| Nombre | Rol |
|---|---|
| Amaro Lopez | Desarrollador Backend |
| _(Integrante 2)_ | _(Rol)_ |
| _(Integrante 3)_ | _(Rol)_ |

---

## Responsabilidades de este Microservicio

- Crear recomendaciones personalizadas para socios.
- Listar todas las recomendaciones del sistema.
- Obtener una recomendación específica por ID.
- Eliminar recomendaciones del sistema.

### Reglas de Negocio implementadas
1. **Longitud mínima del mensaje**: El mensaje debe tener al menos 10 caracteres.
2. **Sin contenido spam**: El mensaje no puede contener palabras como "spam", "publicidad", "oferta" o "compra ahora".
3. **Límite por socio**: Un socio no puede tener más de 10 recomendaciones activas en el sistema.

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Descripción |
|---|---|---|
| `GET` | `/api/recomendaciones` | Listar todas las recomendaciones |
| `GET` | `/api/recomendaciones/{id}` | Obtener una recomendación por ID |
| `POST` | `/api/recomendaciones` | Crear una nueva recomendación |

---

## Enlace Swagger

| Entorno | URL |
|---|---|
| Local | [http://localhost:8087/swagger-ui/index.html](http://localhost:8087/swagger-ui/index.html) |
| Docker | [http://localhost:8087/swagger-ui/index.html](http://localhost:8087/swagger-ui/index.html) |

---

## Instrucciones de Ejecución Local

```bash
cd recomendaciones-services
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8087`

---

## Instrucciones de Despliegue Remoto (Docker)

```bash
docker build -t recomendaciones-services:latest .

docker run -d \
  --name recomendaciones-services \
  -p 8087:8087 \
  -e DB_HOST=<host_bd> \
  -e DB_PORT=5432 \
  -e DB_NAME=db_recomendaciones \
  -e DB_USER=<usuario_bd> \
  -e DB_PASSWORD=<contraseña_bd> \
  recomendaciones-services:latest

# O con Docker Compose:
docker-compose up recomendaciones-services
```

---

## Variables de Entorno Requeridas

| Variable | Descripción | Valor de Ejemplo |
|---|---|---|
| `DB_HOST` | Host de la base de datos PostgreSQL | `localhost` / `db-recomendaciones` |
| `DB_PORT` | Puerto de la base de datos | `5432` |
| `DB_NAME` | Nombre de la base de datos | `db_recomendaciones` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `secret` |
| `SERVER_PORT` | Puerto en que corre el servicio | `8087` |
