FROM openjdk:19
LABEL maintance = "discovery-server"
ADD target/discovery-server-1.0-SNAPSHOT.jar discovery-server.jar
ENTRYPOINT ["java", "-jar", "discovery-server.jar"]