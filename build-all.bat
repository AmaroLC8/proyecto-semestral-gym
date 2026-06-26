@echo off
setlocal

REM =====================================================
REM  Fija JAVA_HOME al JDK 21 (Eclipse Adoptium)
REM  para que mvnw compile correctamente.
REM =====================================================
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo =============================================
echo  JDK activo: %JAVA_HOME%
echo =============================================

REM Verificar que javac esta disponible
javac -version
IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] JDK 21 no encontrado en %JAVA_HOME%
    echo Verifica la ruta e intentalo de nuevo.
    pause
    exit /b 1
)

REM Verificar que Docker este corriendo
docker info >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Docker no esta corriendo. Inicia Docker Desktop e intentalo de nuevo.
    pause
    exit /b 1
)

echo.
echo =====================================
echo LIMPIANDO CONTENEDORES E IMAGENES
echo =====================================

FOR /f %%i IN ('docker ps -aq') DO docker rm -f %%i
FOR /f %%i IN ('docker images -aq') DO docker rmi -f %%i

echo.
echo =====================================
echo BORRANDO CARPETAS TARGET (forzado)
echo =====================================
REM Borrado forzado para evitar "Failed to delete .jar" en Windows
REM cuando el archivo esta bloqueado por otro proceso.

IF EXIST api-gateway\target             rmdir /s /q api-gateway\target
IF EXIST auth-service\target            rmdir /s /q auth-service\target
IF EXIST usuarios-services\target       rmdir /s /q usuarios-services\target
IF EXIST rutinas-services\target        rmdir /s /q rutinas-services\target
IF EXIST reservas-services\target       rmdir /s /q reservas-services\target
IF EXIST seguimientos-services\target   rmdir /s /q seguimientos-services\target
IF EXIST pagos-services\target          rmdir /s /q pagos-services\target
IF EXIST inventario-services\target     rmdir /s /q inventario-services\target
IF EXIST recomendaciones-services\target rmdir /s /q recomendaciones-services\target
IF EXIST soporte-services\target        rmdir /s /q soporte-services\target
IF EXIST review-services\target         rmdir /s /q review-services\target

echo Carpetas target eliminadas.

echo.
echo =====================================
echo COMPILANDO MICROSERVICIOS
echo =====================================

cd api-gateway
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] api-gateway & pause & exit /b 1 )

cd ..\auth-service
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] auth-service & pause & exit /b 1 )

cd ..\usuarios-services
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] usuarios-services & pause & exit /b 1 )

cd ..\rutinas-services
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] rutinas-services & pause & exit /b 1 )

cd ..\reservas-services
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] reservas-services & pause & exit /b 1 )

cd ..\seguimientos-services
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] seguimientos-services & pause & exit /b 1 )

cd ..\pagos-services
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] pagos-services & pause & exit /b 1 )

cd ..\inventario-services
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] inventario-services & pause & exit /b 1 )

cd ..\recomendaciones-services
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] recomendaciones-services & pause & exit /b 1 )

cd ..\soporte-services
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] soporte-services & pause & exit /b 1 )

cd ..\review-services
call .\mvnw clean package -DskipTests
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] review-services & pause & exit /b 1 )

cd ..

echo.
echo =====================================
echo CREANDO IMAGENES DOCKER
echo =====================================

docker compose build --no-cache
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] docker compose build & pause & exit /b 1 )

echo.
echo =====================================
echo LEVANTANDO CONTENEDORES
echo =====================================

docker compose up -d
IF %ERRORLEVEL% NEQ 0 ( echo [FALLO] docker compose up & pause & exit /b 1 )

echo.
echo =====================================
echo  TODOS LOS SERVICIOS LEVANTADOS OK
echo =====================================

endlocal
pause
