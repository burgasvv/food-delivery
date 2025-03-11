FROM maven:3.9.9 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM openjdk:23 AS prod

COPY --from=build target/commit-service-1.0.0.jar commit-service.jar
EXPOSE 9030

ENTRYPOINT ["java", "-jar", "commit-service.jar"]