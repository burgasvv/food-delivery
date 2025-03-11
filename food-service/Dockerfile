FROM maven:3.9.9-openjdk-23 as builder
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM bellsoft/liberica-openjdk-alpine as prod

COPY target/food-service-1.0.0.jar food-service.jar
EXPOSE 9020

ENTRYPOINT ["java", "-jar", "food-service.jar"]