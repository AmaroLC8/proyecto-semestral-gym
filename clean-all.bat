@echo off

echo =====================================
echo LIMPIANDO PROYECTOS MAVEN (GYM)
echo =====================================

cd api-gateway
call .\mvnw clean

cd ..\auth-service
call .\mvnw clean

cd ..\usuarios-services
call .\mvnw clean

cd ..\rutinas-services
call .\mvnw clean

cd ..\reservas-service
call .\mvnw clean

cd ..\seguimientos-services
call .\mvnw clean

cd ..\pagos-services
call .\mvnw clean

cd ..\inventario-services
call .\mvnw clean

cd ..\recomendaciones-service
call .\mvnw clean

cd ..\soporte-service
call .\mvnw clean

cd ..\reviews-service
call .\mvnw clean

cd ..

echo.
echo =====================================
echo LIMPIEZA COMPLETADA
echo =====================================

pause