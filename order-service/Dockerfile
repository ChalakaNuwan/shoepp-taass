FROM openjdk:17-jdk-alpine3.12

ADD target/order-service.jar order-service.jar

EXPOSE 9500

ENTRYPOINT ["java", "-jar","order-service.jar"]
