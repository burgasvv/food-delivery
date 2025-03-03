FROM openjdk
LABEL authors="Burgas"

COPY target/food-service-1.0.0.jar food-service.jar
EXPOSE 9020

ENTRYPOINT ["java", "-jar", "food-service.jar"]