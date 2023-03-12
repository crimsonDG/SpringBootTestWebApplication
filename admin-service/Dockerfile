FROM openjdk:19
LABEL maintance = "admin-service"
ADD target/admin-service-1.0-SNAPSHOT.jar admin-service.jar
ENTRYPOINT ["java", "-jar", "admin-service.jar"]