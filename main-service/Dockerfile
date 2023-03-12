FROM openjdk:19
LABEL maintance = "main-service"
ADD target/main-service-1.0-SNAPSHOT.jar main-service.jar
ENTRYPOINT ["java", "-jar", "main-service.jar"]