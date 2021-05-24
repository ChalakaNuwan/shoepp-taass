FROM openjdk:17-jdk-alpine3.12

ADD target/payment-service.jar payment-service.jar

EXPOSE 9600

ENTRYPOINT ["java", "-jar","payment-service.jar"]
