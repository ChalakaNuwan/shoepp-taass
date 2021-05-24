FROM openjdk:17-jdk-alpine3.12

ADD target/api-gateway.jar api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","api-gateway.jar"]
