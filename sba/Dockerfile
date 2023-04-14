FROM openjdk:19
LABEL maintance = "spring-boot-admin"
ADD target/sba-1.0-SNAPSHOT.jar sba.jar
ENTRYPOINT ["java", "-jar", "sba.jar"]