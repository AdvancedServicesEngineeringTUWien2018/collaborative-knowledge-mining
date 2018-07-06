FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD exploratory-search-app-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]