FROM openjdk
LABEL authors="Burgas"

COPY target/gateway-server-1.0.0.jar gateway-service.jar
EXPOSE 8765

ENTRYPOINT ["java", "-jar", "gateway-service.jar"]