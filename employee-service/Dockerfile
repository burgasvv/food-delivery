FROM bellsoft/liberica-openjdk-alpine
LABEL authors="Burgas"

COPY target/employee-service-1.0.0.jar employee-service.jar
EXPOSE 9010

ENTRYPOINT ["java", "-jar", "employee-service.jar"]