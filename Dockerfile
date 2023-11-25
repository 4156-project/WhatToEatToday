FROM openjdk:8-jdk-alpine
COPY target/RecommendationService-0.0.1-SNAPSHOT.jar recommendation-service.jar
ENTRYPOINT ["java","-jar","/recommendation-service.jar"]
ENV PORT 8080
ENV HOST 0.0.0.0
EXPOSE 8080