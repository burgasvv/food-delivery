FROM openjdk
LABEL authors="Burgas"

COPY target/database-server-1.0.0.jar database-server.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "database-server.jar"]