FROM ubuntu

RUN apt-get update && apt-get install -y openjdk-19-jre-headless wkhtmltopdf xvfb libfontconfig

LABEL maintance = "admin-service"
ADD target/admin-service-1.0-SNAPSHOT.jar admin-service.jar
ENTRYPOINT ["java", "-jar", "admin-service.jar"]