@echo off

echo =====================================
echo LIMPIANDO CONTENEDORES E IMAGENES
echo =====================================

FOR /f %%i IN ('docker ps -aq') DO docker rm -f %%i
FOR /f %%i IN ('docker images -aq') DO docker rmi -f %%i

echo.
echo =====================================
echo COMPILANDO MICROSERVICIOS
echo =====================================

cd api-gateway
call .\mvnw clean package -DskipTests

cd ..\auth-service
call .\mvnw clean package -DskipTests

cd ..\usuarios-services
call .\mvnw clean package -DskipTests

cd ..\rutinas-services
call .\mvnw clean package -DskipTests

cd ..\reservas-service
call .\mvnw clean package -DskipTests

cd ..\seguimientos-services
call .\mvnw clean package -DskipTests

cd ..\pagos-services
call .\mvnw clean package -DskipTests

cd ..\inventario-services
call .\mvnw clean package -DskipTests

cd ..\recomendaciones-service
call .\mvnw clean package -DskipTests

cd ..\soporte-service
call .\mvnw clean package -DskipTests

cd ..\reviews-service
call .\mvnw clean package -DskipTests

cd ..

echo.
echo =====================================
echo CREANDO IMAGENES DOCKER
echo =====================================

docker compose build

echo.
echo =====================================
echo LEVANTANDO CONTENEDORES
echo =====================================

docker compose up -d

pause