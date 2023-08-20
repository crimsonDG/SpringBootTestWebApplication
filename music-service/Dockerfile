FROM openjdk:19
LABEL maintance = "music-service"
ADD target/music-service-1.0-SNAPSHOT.jar music-service.jar
ENTRYPOINT ["java", "-jar", "music-service.jar"]