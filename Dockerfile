FROM openjdk:17-jdk-slim

ARG ENVIRONMENT

ENV SPRING_PROFILES_ACTIVE ${ENVIRONMENT}
EXPOSE 8080

RUN mkdir /deployment
COPY build/libs/*.jar /deployment/app.jar

ENTRYPOINT ["java","-jar","/deployment/app.jar"]