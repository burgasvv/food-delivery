FROM openjdk
LABEL authors="Burgas"

COPY target/media-service-1.0.0.jar media-service.jar
EXPOSE 9040

ENTRYPOINT ["java", "-jar", "media-service.jar"]