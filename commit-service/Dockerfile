FROM maven:3.9.9-openjdk-23 as builder
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM bellsoft/liberica-openjdk-alpine as prod

COPY target/commit-service-1.0.0.jar commit-service.jar
EXPOSE 9030

ENTRYPOINT ["java", "-jar", "commit-service.jar"]