FROM openjdk:17-jdk-alpine3.12

ADD target/cart-service.jar cart-service.jar

EXPOSE 9400

ENTRYPOINT ["java", "-jar","cart-service.jar"]
