FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/fullname-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 1062

ENTRYPOINT ["java", "-jar", "app.jar"]