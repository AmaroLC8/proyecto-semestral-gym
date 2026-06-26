# 🏋️ Microservicio: usuarios-services

## Contexto o Dominio del Sistema

Este microservicio forma parte del sistema de gestión integral de un gimnasio universitario. Su dominio es la **gestión de usuarios/socios**, encargándose del registro, consulta y eliminación de los clientes del gimnasio. Es un servicio central del que dependen otros microservicios como `pagos-services`, `reservas-services` y `seguimientos-services`.

---

## Integrantes del Equipo

| Nombre | Rol |
|---|---|
| Amaro Lopez | Desarrollador Backend |
| _(Integrante 2)_ | _(Rol)_ |
| _(Integrante 3)_ | _(Rol)_ |

---

## Responsabilidades de este Microservicio

- Registrar nuevos usuarios (socios) en el sistema del gimnasio.
- Consultar la lista completa de usuarios registrados.
- Obtener la información de un usuario por su ID.
- Eliminar usuarios del sistema.
- Validar que el correo sea único por usuario.
- Validar que el tipo de membresía sea uno de los valores permitidos (`BASICA`, `ESTANDAR`, `PREMIUM`, `VIP`).
- Exponer un endpoint `/usuarios/{id}/exists` para la verificación remota por parte de otros servicios.

### Reglas de Negocio implementadas
1. **Correo único**: No se pueden registrar dos usuarios con el mismo correo electrónico.
2. **Membresía válida**: El campo `tipoMembresia` solo acepta `BASICA`, `ESTANDAR`, `PREMIUM` o `VIP`.
3. **Nombre completo**: El nombre debe contener al menos dos palabras (nombre y apellido).

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Descripción |
|---|---|---|
| `GET` | `/api/usuarios` | Listar todos los usuarios |
| `GET` | `/api/usuarios/{id}` | Obtener un usuario por ID |
| `POST` | `/api/usuarios` | Crear un nuevo usuario |
| `DELETE` | `/api/usuarios/{id}` | Eliminar un usuario |

---

## Enlace Swagger

| Entorno | URL |
|---|---|
| Local | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |
| Docker | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |

---

## Instrucciones de Ejecución Local

### Pre-requisitos
- Java 17+
- Maven 3.8+
- Base de datos PostgreSQL corriendo en `localhost:5432`

### Pasos
```bash
# 1. Navegar al directorio del servicio
cd usuarios-services

# 2. Compilar el proyecto
./mvnw clean package -DskipTests

# 3. Ejecutar el microservicio
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8081`

---

## Instrucciones de Despliegue Remoto (Docker)

```bash
# 1. Construir la imagen Docker
docker build -t usuarios-services:latest .

# 2. Ejecutar el contenedor (standalone)
docker run -d \
  --name usuarios-services \
  -p 8081:8081 \
  -e DB_HOST=<host_bd> \
  -e DB_PORT=5432 \
  -e DB_NAME=db_usuarios \
  -e DB_USER=<usuario_bd> \
  -e DB_PASSWORD=<contraseña_bd> \
  usuarios-services:latest

# 3. O bien, levantar con Docker Compose desde la raíz del proyecto
docker-compose up usuarios-services
```

---

## Variables de Entorno Requeridas

| Variable | Descripción | Valor de Ejemplo |
|---|---|---|
| `DB_HOST` | Host de la base de datos PostgreSQL | `localhost` / `db-usuarios` |
| `DB_PORT` | Puerto de la base de datos | `5432` |
| `DB_NAME` | Nombre de la base de datos | `db_usuarios` |
| `DB_USER` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña de la base de datos | `secret` |
| `SERVER_PORT` | Puerto en que corre el servicio | `8081` |
