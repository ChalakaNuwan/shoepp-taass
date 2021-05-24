FROM openjdk:17-jdk-alpine3.12

ADD target/catalog-service.jar catalog-service.jar

EXPOSE 9200

ENTRYPOINT ["java", "-jar","catalog-service.jar"]
