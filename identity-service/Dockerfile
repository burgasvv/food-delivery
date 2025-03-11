FROM maven:3.9.9-openjdk-23 as builder
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM bellsoft/liberica-openjdk-alpine as prod

COPY target/identity-service-1.0.0.jar identity-service.jar
EXPOSE 8888

ENTRYPOINT ["java", "-jar", "identity-service.jar"]