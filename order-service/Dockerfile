FROM openjdk:8-jdk-alpine

ADD target/order-service.jar order-service.jar

EXPOSE 9500

ENTRYPOINT ["java", "-jar","order-service.jar"]
