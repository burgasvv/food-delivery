FROM maven:3.9.9 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM openjdk:23 AS prod

COPY --from=build target/employee-service-1.0.0.jar employee-service.jar
EXPOSE 9010

ENTRYPOINT ["java", "-jar", "employee-service.jar"]