FROM openjdk:17-jdk-alpine3.12

ADD target/user-service.jar user-service.jar

EXPOSE 9300

ENTRYPOINT ["java", "-jar","user-service.jar"]
