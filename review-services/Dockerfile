# copiamos el jar que ya compilamos localmente con mvnw
# asi docker no necesita internet ni maven para nada
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]