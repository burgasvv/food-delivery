FROM openjdk
LABEL authors="Burgas"

COPY target/department-service-1.0.0.jar department-service.jar
EXPOSE 9000

ENTRYPOINT ["java", "-jar", "department-service.jar"]