# 🏋️ Microservicio: inventario-services

## Contexto o Dominio del Sistema

Este microservicio gestiona el **inventario de productos** del gimnasio. Controla el catálogo de artículos disponibles para venta o uso, incluyendo equipamiento, suplementos, accesorios, ropa deportiva y servicios. Es consumido por otros servicios del sistema para verificar disponibilidad.

---

## Integrantes del Equipo

| Nombre | Rol |
|---|---|
| Amaro Lopez | Desarrollador Backend |
| _(Integrante 2)_ | _(Rol)_ |
| _(Integrante 3)_ | _(Rol)_ |

---

## Responsabilidades de este Microservicio

- Registrar nuevos productos en el inventario.
- Listar el catálogo completo de productos disponibles.
- Obtener el detalle de un producto por su ID.
- Eliminar productos del inventario.
- Controlar el stock y precio de cada producto.

### Reglas de Negocio implementadas
1. **Stock válido**: El stock debe estar entre 0 y 10.000 unidades.
2. **Precio válido**: El precio debe ser mayor a $0 y no superar $100.000.000.
3. **Categoría válida**: Solo se aceptan `EQUIPAMIENTO`, `SUPLEMENTO`, `ACCESORIO`, `ROPA` o `SERVICIO`.

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Descripción |
|---|---|---|
| `GET` | `/api/inventario` | Listar todos los productos |
| `GET` | `/api/inventario/{id}` | Obtener un producto por ID |
| `POST` | `/api/inventario` | Crear un nuevo producto |
| `DELETE` | `/api/inventario/{id}` | Eliminar un producto |

---

## Enlace Swagger

| Entorno | URL |
|---|---|
| Local | [http://localhost:8086/swagger-ui/index.html](http://localhost:8086/swagger-ui/index.html) |
| Docker | [http://localhost:8086/swagger-ui/index.html](http://localhost:8086/swagger-ui/index.html) |

---

## Instrucciones de Ejecución Local

```bash
cd inventario-services
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8086`

---

## Instrucciones de Despliegue Remoto (Docker)

```bash
docker build -t inventario-services:latest .

docker run -d \
  --name inventario-services \
  -p 8086:8086 \
  -e DB_HOST=<host_bd> \
  -e DB_PORT=5432 \
  -e DB_NAME=db_inventario \
  -e DB_USER=<usuario_bd> \
  -e DB_PASSWORD=<contraseña_bd> \
  inventario-services:latest

# O con Docker Compose:
docker-compose up inventario-services
```

---

## Variables de Entorno Requeridas

| Variable | Descripción | Valor de Ejemplo |
|---|---|---|
| `DB_HOST` | Host de la base de datos PostgreSQL | `localhost` / `db-inventario` |
| `DB_PORT` | Puerto de la base de datos | `5432` |
| `DB_NAME` | Nombre de la base de datos | `db_inventario` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `secret` |
| `SERVER_PORT` | Puerto en que corre el servicio | `8086` |
