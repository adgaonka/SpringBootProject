FROM openjdk:8-jdk-alpine
WORKDIR /opt/app
COPY target/*.jar /opt/app/service.jar
CMD ["java","-jar","/opt/app/service.jar"]