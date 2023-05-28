FROM adoptopenjdk:11-jre-hotspot
EXPOSE 8080
ADD target/gatewayproject-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]