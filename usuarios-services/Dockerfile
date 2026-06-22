FROM eclipse-temurin:21
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "app.jar"]
