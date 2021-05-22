FROM openjdk:8-jdk-alpine

ADD target/cart-service.jar cart-service.jar

EXPOSE 9400

ENTRYPOINT ["java", "-jar","cart-service.jar"]
