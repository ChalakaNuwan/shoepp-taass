FROM openjdk:17-jdk-alpine3.12

ADD target/eureka-server.jar eureka-server.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar","eureka-server.jar"]
