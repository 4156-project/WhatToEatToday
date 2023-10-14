FROM java:8
ADD /target/RecommendationService-0.0.1-SNAPSHOT.jar /RecommendationService-0.0.1-SNAPSHOT.jar
RUN bash -c 'touch /RecommendationService-0.0.1-SNAPSHOT.jar'
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/RecommendationService-0.0.1-SNAPSHOT.jar"]
# 指定维护者的名字
MAINTAINER lijiehuang