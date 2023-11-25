FROM openjdk:8-jdk-alpine
COPY target/RecommendationService-0.0.1-SNAPSHOT.jar RecommendationService-0.0.1.jar
ENTRYPOINT ["java","-jar","/RecommendationService-0.0.1.jar"]
ENV PORT 8080
ENV HOST 0.0.0.0
EXPOSE 8080