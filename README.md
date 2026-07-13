# 🏋️ Sistema de Gestión de Gimnasio - Grupo 1

Sistema de microservicios para la gestión integral de un gimnasio, desarrollado con **Spring Boot 3** y **Java 17**.

## 📋 Microservicios

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **api-gateway** | 9090 | API Gateway (Spring Cloud Gateway) - Punto de entrada único |
| **auth-service** | 8085 | Autenticación y registro de usuarios |
| **usuarios-services** | 9091 | Gestión de usuarios/socios del gimnasio |
| **inventario-services** | 9092 | Gestión de productos e inventario |
| **pagos-services** | 9093 | Procesamiento de pagos y facturación |
| **reservas-services** | 9095 | Reservas de clases y servicios |
| **rutinas-services** | 9096 | Gestión de rutinas de ejercicio |
| **seguimientos-services** | 9094 | Seguimiento de progreso de socios |
| **recomendaciones-services** | 9097 | Recomendaciones personalizadas |
| **review-services** | 9099 | Reseñas y calificaciones |
| **soporte-services** | 9098 | Tickets de soporte técnico |

## 🚀 Requisitos

- **Java 17**
- **Maven 3.8+**
- **MySQL 8.0**
- **Docker** (opcional, para despliegue con contenedores)

## ⚙️ Configuración

### Base de Datos
Cada microservicio usa su propia base de datos MySQL:

```
db_usuarios, db_inventario, db_pagos, db_reservas,
db_rutinas, db_seguimientos, db_recomendaciones,
db_reviews, db_soporte
```

Configuración por defecto:
- **Host**: `localhost:3306`
- **Usuario**: `root`
- **Password**: (vacío)

### Puertos
Todos los microservicios se comunican a través del **API Gateway** en el puerto `8080`.

## 🔧 Ejecución

### Opción 1: Scripts batch (Windows)

```bash
# Compilar todos los servicios
build-all.bat

# Limpiar todos los servicios
clean-all.bat
```

### Opción 2: Docker Compose

```bash
docker-compose up --build
```

### Opción 3: Ejecución manual

```bash
# Desde la raíz de cada servicio
cd auth-service && mvnw spring-boot:run
cd usuarios-services && mvnw spring-boot:run
# ... etc.
```

## 📡 API Endpoints

Todos los endpoints se acceden a través del API Gateway: `http://localhost:8080/api/`

| Módulo | Ruta Base | Swagger UI |
|--------|-----------|------------|
| Auth | `/api/auth/**` | `/doc/swagger-ui.html` |
| Usuarios | `/api/usuarios/**` | `/doc/swagger-ui.html` |
| Inventario | `/api/inventario/**` | `/doc/swagger-ui.html` |
| Pagos | `/api/pagos/**` | `/doc/swagger-ui.html` |
| Reservas | `/api/reservas/**` | `/doc/swagger-ui.html` |
| Rutinas | `/api/rutinas/**` | `/doc/swagger-ui.html` |
| Seguimientos | `/api/seguimientos/**` | `/doc/swagger-ui.html` |
| Recomendaciones | `/api/recomendaciones/**` | `/doc/swagger-ui.html` |
| Reviews | `/api/reviews/**` | `/doc/swagger-ui.html` |
| Soporte | `/api/soporte/**` | `/doc/swagger-ui.html` |

## 🧪 Pruebas

Cada microservicio incluye pruebas unitarias con **JUnit 5** y **Mockito**:

```bash
# Ejecutar pruebas de un servicio específico
cd usuarios-services && mvnw test

# Ejecutar todas las pruebas
mvnw test
```

## 🏗️ Arquitectura

- **Patrón CSR**: Controller → Service → Repository
- **DTOs** con validaciones Jakarta Bean Validation
- **Excepciones personalizadas** (`ResourceNotFoundException`)
- **HATEOAS** con Spring HATEOAS (assemblers)
- **Swagger/OpenAPI 3** para documentación
- **Spring Cloud Gateway** como API Gateway
- **Comunicación REST** entre microservicios con WebClient

## 📊 Reglas de Negocio

- **Auth**: Login con validación de credenciales, registro con email único
- **Pagos**: Cálculo automático de IVA (19%) y descuentos
- **Soporte**: Tickets con estado PENDIENTE/RESUELTO y fecha automática
- **Usuarios**: Validación de correo duplicado
- **Rutinas**: Validación de duración mínima (> 0 minutos)
- **Inventario**: Validación de stock y precio no negativos
- **Reviews**: Validación de calificación entre 1 y 5

## 👥 Equipo

Desarrollado por **Amaro Lopez y Carolina Garrido** - Proyecto Semestral Gimnasio
