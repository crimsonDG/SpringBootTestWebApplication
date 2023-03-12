FROM openjdk:19
LABEL maintance = "api-gateway"
ADD target/api-gateway-1.0-SNAPSHOT.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]