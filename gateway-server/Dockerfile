FROM maven:3.9.9-openjdk-23 as builder
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ ./src/
RUN mvn clean package

FROM bellsoft/liberica-openjdk-alpine as prod

COPY target/gateway-server-1.0.0.jar gateway-server.jar
EXPOSE 8765

ENTRYPOINT ["java", "-jar", "gateway-server.jar"]