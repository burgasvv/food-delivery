FROM maven:3.9.9-openjdk-23 as builder
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM bellsoft/liberica-openjdk-alpine as prod

COPY target/media-service-1.0.0.jar media-service.jar
EXPOSE 9040

ENTRYPOINT ["java", "-jar", "media-service.jar"]