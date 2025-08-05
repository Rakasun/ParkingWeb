# Usa una imagen base de Java
FROM openjdk:17-jdk-alpine
# Copia el jar generado al contenedor
COPY target/*.jar app.jar
# Expone el puerto (ajusta si tu app usa otro)
EXPOSE 8080
# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "/app.jar"]