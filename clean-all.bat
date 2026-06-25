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

cd ..\reservas-services
call .\mvnw clean

cd ..\seguimientos-services
call .\mvnw clean

cd ..\pagos-services
call .\mvnw clean

cd ..\inventario-services
call .\mvnw clean

cd ..\recomendaciones-services
call .\mvnw clean

cd ..\soporte-services
call .\mvnw clean

cd ..\review-services
call .\mvnw clean

cd ..

echo.
echo =====================================
echo LIMPIEZA COMPLETADA
echo =====================================

pause