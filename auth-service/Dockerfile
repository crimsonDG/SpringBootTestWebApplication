FROM openjdk:19
LABEL maintance = "auth-service"
ADD target/auth-service-1.0-SNAPSHOT.jar auth-service.jar
ENTRYPOINT ["java", "-jar", "auth-service.jar"]