FROM openjdk:19
LABEL maintance = "config-server"
ADD target/config-server-1.0-SNAPSHOT.jar config-server.jar
ENTRYPOINT ["java", "-jar", "config-server.jar"]