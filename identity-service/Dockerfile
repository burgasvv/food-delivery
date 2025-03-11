FROM maven:3.9.9 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM openjdk:23 AS prod

COPY --from=build target/identity-service-1.0.0.jar identity-service.jar
EXPOSE 8888

ENTRYPOINT ["java", "-jar", "identity-service.jar"]