FROM openjdk:8-jdk-alpine

ADD target/user-service.jar user-service.jar

EXPOSE 9300

ENTRYPOINT ["java", "-jar","user-service.jar"]
