FROM maven:3.9.9-openjdk-23 as builder
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM bellsoft/liberica-openjdk-alpine as prod

COPY target/database-server-1.0.0.jar database-server.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "database-server.jar"]