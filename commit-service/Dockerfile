FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

COPY target/commit-service-1.0.0.jar commit-service.jar
EXPOSE 9030

ENTRYPOINT ["java", "-jar", "commit-service.jar"]