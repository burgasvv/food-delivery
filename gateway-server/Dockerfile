FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

COPY target/gateway-server-1.0.0.jar gateway-server.jar
EXPOSE 8765

ENTRYPOINT ["java", "-jar", "gateway-server.jar"]