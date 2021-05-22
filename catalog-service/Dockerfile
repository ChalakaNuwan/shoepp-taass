FROM openjdk:8-jdk-alpine

ADD target/catalog-service.jar catalog-service.jar

EXPOSE 9200

ENTRYPOINT ["java", "-jar","catalog-service.jar"]
