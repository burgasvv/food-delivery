FROM openjdk
LABEL authors="Burgas"

COPY target/identity-service-1.0.0.jar identity-service.jar
EXPOSE 8888

ENTRYPOINT ["java", "-jar", "identity-service.jar"]