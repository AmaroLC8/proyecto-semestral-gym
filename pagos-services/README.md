# 🏋️ Microservicio: pagos-services

## Contexto o Dominio del Sistema

Este microservicio gestiona el **procesamiento de pagos** en el sistema del gimnasio. Calcula automáticamente el IVA (19%) y el monto total a pagar, considerando los descuentos aplicados. También se integra con `usuarios-services` y `reservas-services` para obtener información remota enriquecida.

---

## Integrantes del Equipo

| Nombre | Rol |
|---|---|
| Amaro Lopez | Desarrollador Backend |
| _(Integrante 2)_ | _(Rol)_ |
| _(Integrante 3)_ | _(Rol)_ |

---

## Responsabilidades de este Microservicio

- Procesar pagos calculando descuento, IVA (19%) y total a pagar.
- Listar, consultar y eliminar pagos.
- Buscar pagos por medio de pago o rango de fechas.
- Integración con `usuarios-services` y `reservas-services` vía WebClient.

### Reglas de Negocio implementadas
1. **Descuento máximo**: El descuento no puede superar el 50%.
2. **Monto máximo**: El valor neto no puede superar $10.000.000 (requiere aprobación manual).
3. **Medio de pago válido**: Solo se aceptan `EFECTIVO`, `TARJETA_CREDITO`, `TARJETA_DEBITO` o `TRANSFERENCIA`.

---

## Rutas Principales del Gateway

| Método | Ruta Gateway | Descripción |
|---|---|---|
| GET | /api/pagos | Listar todos los pagos |
| GET | /api/pagos/{id} | Obtener un pago por ID |
| POST | /api/pagos | Procesar y crear un nuevo pago |
| PUT | /api/pagos/{id} | Actualizar un pago existente |
| DELETE | /api/pagos/{id} | Eliminar un pago |
| GET | /api/pagos/{id}/detalle | Obtener pago con datos remotos |
| GET | /api/pagos/compra/{id}/total | Total de pagos por compra |

---

## Enlace Swagger

| Entorno | URL |
|---|---|
| Local | http://localhost:8085/swagger-ui/index.html |
| Docker | http://localhost:8085/swagger-ui/index.html |

---

## Instrucciones de Ejecución Local

```bash
cd pagos-services
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

El servicio estará disponible en: http://localhost:8085

---

## Instrucciones de Despliegue Remoto (Docker)

```bash
docker build -t pagos-services:latest .

docker run -d \
  --name pagos-services \
  -p 8085:8085 \
  -e DB_HOST=db-pagos \
  -e DB_PORT=5432 \
  -e DB_NAME=db_pagos \
  -e DB_USER=postgres \
  -e DB_PASSWORD=secret \
  pagos-services:latest

docker-compose up pagos-services
```

---

## Variables de Entorno Requeridas

| Variable | Descripción | Valor de Ejemplo |
|---|---|---|
| DB_HOST | Host de la base de datos PostgreSQL | localhost o db-pagos |
| DB_PORT | Puerto de la base de datos | 5432 |
| DB_NAME | Nombre de la base de datos | db_pagos |
| DB_USER | Usuario de la base de datos | postgres |
| DB_PASSWORD | Contrasena de la base de datos | secret |
| SERVER_PORT | Puerto en que corre el servicio | 8085 |
| USUARIOS_SERVICE_URL | URL del microservicio de usuarios | http://usuarios-services:8081 |
| RESERVAS_SERVICE_URL | URL del microservicio de reservas | http://reservas-services:8083 |
