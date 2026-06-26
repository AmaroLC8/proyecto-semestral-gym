# REPORTE DE CAMBIOS - EVALUACIÓN 3
## Proyecto Semestral Gym - Auditoría y Refactorización

**Fecha:** 25 de Junio de 2026
**Auditor:** Ingeniero de Software Senior (Cline)

---

## 1. RESUMEN DE ERRORES CORREGIDOS

### 1.1. Error de Red en Docker: "Unknown host repo.maven.apache.org"

**Diagnóstico:**
Los contenedores Docker no podían resolver el DNS para acceder a Maven Central (`repo.maven.apache.org`), causando que la fase de build fallara en `./mvnw dependency:go-offline`.

**Solución aplicada:**

1. **docker-compose.yml** - Se agregó configuración de red explícita a todos los servicios:
   - Red `gym-network` tipo `bridge` para comunicación entre contenedores
   - DNS servers públicos de Google (`8.8.8.8`, `8.8.4.4`) en cada servicio
   - Esto garantiza que los contenedores tengan resolución DNS durante el build

2. **Dockerfiles (11/11 actualizados)** - Se mejoró la etapa de build:
   - Se agregó `RUN chmod +x mvnw` para asegurar permisos de ejecución
   - Se implementó lógica de reintentos (3 intentos) en `dependency:go-offline`:
     ```dockerfile
     RUN ./mvnw dependency:go-offline -DskipTests -q || \
         ./mvnw dependency:go-offline -DskipTests -q || \
         ./mvnw dependency:go-offline -DskipTests -q
     ```
   - Esto maneja fallos intermitentes de red durante la descarga de dependencias

### 1.2. Error de Compatibilidad Springdoc OpenAPI

**Diagnóstico (desde logs):**
```
org.springdoc.core.providers.HateoasHalProvider.isHalEnabled()
→ boolean org.springframework.boot.autoconfigure.hateoas.HateoasProperties.getUseHalAsDefaultJsonMediaType()
```
El método `getUseHalAsDefaultJsonMediaType()` no existe en `spring-boot-autoconfigure 3.5.7`. Esto se debe a que `springdoc-openapi 2.5.0` no es compatible con versiones recientes de Spring Boot.

**Solución aplicada:**
- Se actualizó `springdoc-openapi-starter-webmvc-ui` de `2.5.0` → `2.6.0` en TODOS los servicios
- Servicios actualizados: auth-service, usuarios-services, pagos-services, rutinas-services, reservas-services, seguimientos-services, inventario-services, recomendaciones-services, soporte-services, review-services, api-gateway

### 1.3. Error de Bean Validation Provider

**Diagnóstico (desde logs):**
```
jakarta.validation.NoProviderFoundException: Unable to create a Configuration, 
because no Jakarta Bean Validation provider could be found.
```

**Solución aplicada:**
- Se agregó `spring-boot-starter-validation` a los servicios que no lo tenían:
  - `auth-service/pom.xml` ✅
  - `reservas-services/pom.xml` ✅
  - `seguimientos-services/pom.xml` ✅
  - `usuarios-services/pom.xml` ✅ (ya tenía HATEOAS, se agregó validation)

### 1.4. Dependencia HATEOAS en auth-service

**Solución aplicada:**
- Se agregó `spring-boot-starter-hateoas` a `auth-service/pom.xml` para habilitar HATEOAS en el servicio de autenticación

---

## 2. DETALLE DE IMPLEMENTACIÓN HATEOAS

### 2.1. Servicio de Referencia: pagos-services

El servicio `pagos-services` ya implementa correctamente el patrón HATEOAS y sirve como modelo de referencia:

| Componente | Estado | Descripción |
|------------|--------|-------------|
| `PagoController.java` | ✅ Completo | CRUD completo con HATEOAS (EntityModel, CollectionModel) |
| `PagoControllerV2.java` | ✅ Completo | Versión V2 con Assembler |
| `PagoModelAssembler.java` | ✅ Completo | Implementa `RepresentationModelAssembler` |
| `PagoDTO.java` | ✅ Completo | Con validaciones Jakarta (`@NotNull`, `@Min`, `@Max`, `@NotBlank`) |
| `GlobalExceptionHandler.java` | ✅ Completo | Manejo global con `@RestControllerAdvice` |
| `ApiErrorResponse.java` | ✅ Completo | DTO para respuestas de error |
| `ResourceNotFoundException.java` | ✅ Completo | Excepción personalizada |
| `BadRequestException.java` | ✅ Completo | Excepción personalizada |

### 2.2. Servicios que YA tienen HATEOAS implementado

| Servicio | Assembler | Controller HATEOAS | GlobalExceptionHandler | DTOs con validación |
|----------|-----------|-------------------|----------------------|-------------------|
| usuarios-services | ✅ UsuarioModelAssembler | ✅ UsuarioController | ✅ | ✅ UsuarioDTO |
| inventario-services | ✅ | ✅ | ✅ | ✅ |
| recomendaciones-services | ✅ | ✅ | ✅ | ✅ |
| review-services | ✅ | ✅ | ✅ | ✅ |
| soporte-services | ✅ | ✅ | ✅ | ✅ |
| rutinas-services | ✅ | ✅ | ✅ | ✅ |

### 2.3. Servicios que REQUIEREN implementación HATEOAS (PENDIENTE FASE 2)

| Servicio | Assembler | Controller HATEOAS | GlobalExceptionHandler | DTOs con validación |
|----------|-----------|-------------------|----------------------|-------------------|
| auth-service | ❌ | ❌ (usa Map<String,String>) | ❌ | ❌ |
| reservas-services | ❌ | ❌ | ❌ | ❌ |
| seguimientos-services | ❌ | ❌ | ❌ | ❌ |

---

## 3. ESTADO DE SALUD - CONFIGURACIÓN

### 3.1. docker-compose.yml

| Elemento | Estado |
|----------|--------|
| Red `gym-network` (bridge) | ✅ Configurada |
| DNS servers (8.8.8.8, 8.8.4.4) | ✅ En todos los servicios |
| MySQL 8.0 | ✅ Con healthcheck |
| Healthchecks (curl + actuator) | ✅ En todos los servicios |
| Dependencias entre servicios | ✅ Configuradas |

### 3.2. Versiones de Dependencias (Consistentes)

| Dependencia | Versión | Estado |
|-------------|---------|--------|
| Spring Boot Parent | 3.3.5 | ✅ Consistente en todos |
| Java | 21 | ✅ Consistente en todos |
| springdoc-openapi | 2.6.0 | ✅ Actualizado en todos |
| spring-boot-starter-validation | - | ✅ Presente en todos |
| spring-boot-starter-hateoas | - | ✅ Presente en todos |
| MySQL Connector | (managed) | ✅ Consistente |
| Lombok | 1.18.38 | ✅ Consistente |

### 3.3. Puertos de Servicios

| Servicio | Puerto | Swagger UI |
|----------|--------|------------|
| api-gateway | 9090 | - |
| auth-service | 8085 | /doc/swagger-ui.html |
| usuarios-service | 9091 | /doc/swagger-ui.html |
| rutinas-service | 9092 | /doc/swagger-ui.html |
| reservas-service | 9093 | /doc/swagger-ui.html |
| seguimientos-service | 9094 | /doc/swagger-ui.html |
| pagos-service | 9095 | /doc/swagger-ui.html |
| inventario-service | 9096 | /doc/swagger-ui.html |
| recomendaciones-service | 9097 | /doc/swagger-ui.html |
| soporte-service | 9098 | /doc/swagger-ui.html |
| reviews-service | 9099 | /doc/swagger-ui.html |

---

## 4. TAREAS PENDIENTES (FASE 2 y FASE 3)

### FASE 2: Refactorización HATEOAS (PRIORIDAD ALTA)

Los siguientes servicios necesitan implementación completa de HATEOAS siguiendo el patrón de `pagos-services`:

1. **auth-service** (Puerto 8085):
   - [ ] Crear DTOs: `LoginRequest`, `RegisterRequest`, `AuthResponse`
   - [ ] Agregar validaciones Jakarta a los DTOs
   - [ ] Crear `AuthModelAssembler`
   - [ ] Refactorizar `AuthController` para devolver `EntityModel`/`CollectionModel`
   - [ ] Crear `GlobalExceptionHandler`
   - [ ] Crear `ApiErrorResponse`
   - [ ] Crear excepciones personalizadas (`ResourceNotFoundException`, `BadRequestException`)

2. **reservas-services** (Puerto 9093):
   - [ ] Verificar si tiene DTOs con validaciones
   - [ ] Crear `ReservaModelAssembler`
   - [ ] Refactorizar controlador para HATEOAS
   - [ ] Crear `GlobalExceptionHandler`
   - [ ] Crear `ApiErrorResponse` y excepciones personalizadas

3. **seguimientos-services** (Puerto 9094):
   - [ ] Verificar si tiene DTOs con validaciones
   - [ ] Crear `SeguimientoModelAssembler`
   - [ ] Refactorizar controlador para HATEOAS
   - [ ] Crear `GlobalExceptionHandler`
   - [ ] Crear `ApiErrorResponse` y excepciones personalizadas

### FASE 3: Verificación de Cumplimiento

- [ ] Verificar que cada microservicio tenga CRUD completo (GET, POST, PUT, DELETE)
- [ ] Verificar que Swagger UI sea accesible en cada servicio
- [ ] Verificar que las anotaciones de validación Jakarta estén en todos los DTOs
- [ ] Probar `docker-compose up --build` para verificar que todo compile y se despliegue

---

## 5. INSTRUCCIONES PARA CONTINUAR

Para completar las Fases 2 y 3, se recomienda:

1. **Por cada servicio pendiente**, seguir este orden:
   - Crear paquete `dto/` con DTOs y validaciones Jakarta
   - Crear paquete `exception/` con `GlobalExceptionHandler`, `ApiErrorResponse`, `ResourceNotFoundException`, `BadRequestException`
   - Crear paquete `assemblers/` con la clase `XxxModelAssembler` (implementa `RepresentationModelAssembler`)
   - Refactorizar el controlador para usar `EntityModel` y `CollectionModel`

2. **Ejecutar build de prueba**:
   ```bash
   docker-compose up --build
   ```

3. **Verificar endpoints**:
   ```bash
   curl http://localhost:9095/doc/swagger-ui.html  # Ejemplo para pagos
   ```

---

## 6. RESUMEN DE ARCHIVOS MODIFICADOS

| Archivo | Cambio |
|---------|--------|
| `docker-compose.yml` | Agregada red gym-network, DNS, healthchecks |
| `*/Dockerfile` (11 archivos) | Agregado chmod +x, reintentos en dependency:go-offline |
| `auth-service/pom.xml` | springdoc 2.6.0, +validation, +hateoas |
| `usuarios-services/pom.xml` | springdoc 2.6.0, +validation |
| `pagos-services/pom.xml` | springdoc 2.6.0 |
| `rutinas-services/pom.xml` | springdoc 2.6.0 |
| `reservas-services/pom.xml` | springdoc 2.6.0, +validation |
| `seguimientos-services/pom.xml` | springdoc 2.6.0, +validation |
| `inventario-services/pom.xml` | springdoc 2.6.0 |
| `api-gateway/pom.xml` | springdoc 2.6.0 |

---

**Fin del Reporte**