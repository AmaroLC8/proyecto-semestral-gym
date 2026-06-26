# 🏋️ Microservicio: review-services

## Contexto o Dominio del Sistema

Este microservicio gestiona las **reseñas y calificaciones** de los productos del gimnasio. Los socios pueden calificar y comentar los productos o servicios adquiridos (equipamiento, clases, suplementos, etc.), generando retroalimentación valiosa para la mejora continua del gimnasio.

---

## Integrantes del Equipo

| Nombre | Rol |
|---|---|
| Amaro Lopez | Desarrollador Backend |
| _(Integrante 2)_ | _(Rol)_ |
| _(Integrante 3)_ | _(Rol)_ |

---

## Responsabilidades de este Microservicio

- Registrar nuevas reseñas con calificación (1-5 estrellas) y comentario.
- Listar todas las reseñas del sistema.
- Obtener una reseña específica por ID.
- Eliminar reseñas del catálogo.

### Reglas de Negocio implementadas
1. **Calificación válida**: La calificación debe estar entre 1 y 5 estrellas.
2. **Comentario mínimo**: El comentario debe tener al menos 10 caracteres.
3. **Límite de reviews por producto**: Un producto no puede tener más de 100 reviews en el sistema.

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Descripción |
|---|---|---|
| `GET` | `/api/reviews` | Listar todas las reseñas |
| `GET` | `/api/reviews/{id}` | Obtener una reseña por ID |
| `POST` | `/api/reviews` | Crear una nueva reseña |
| `DELETE` | `/api/reviews/{id}` | Eliminar una reseña |

---

## Enlace Swagger

| Entorno | URL |
|---|---|
| Local | [http://localhost:8089/swagger-ui/index.html](http://localhost:8089/swagger-ui/index.html) |
| Docker | [http://localhost:8089/swagger-ui/index.html](http://localhost:8089/swagger-ui/index.html) |

---

## Instrucciones de Ejecución Local

```bash
cd review-services
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8089`

---

## Instrucciones de Despliegue Remoto (Docker)

```bash
docker build -t review-services:latest .

docker run -d \
  --name review-services \
  -p 8089:8089 \
  -e DB_HOST=<host_bd> \
  -e DB_PORT=5432 \
  -e DB_NAME=db_reviews \
  -e DB_USER=<usuario_bd> \
  -e DB_PASSWORD=<contraseña_bd> \
  review-services:latest

# O con Docker Compose:
docker-compose up review-services
```

---

## Variables de Entorno Requeridas

| Variable | Descripción | Valor de Ejemplo |
|---|---|---|
| `DB_HOST` | Host de la base de datos PostgreSQL | `localhost` / `db-reviews` |
| `DB_PORT` | Puerto de la base de datos | `5432` |
| `DB_NAME` | Nombre de la base de datos | `db_reviews` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `secret` |
| `SERVER_PORT` | Puerto en que corre el servicio | `8089` |
