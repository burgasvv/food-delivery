FROM maven:3.9.9 AS build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM openjdk:23 AS prod

COPY --from=build target/database-server-1.0.0.jar database-server.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "database-server.jar"]