FROM openjdk:21-jdk
LABEL authors="andrey"
ARG JAR_FILE=./build/libs/taskmasterserver-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]