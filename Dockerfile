FROM openjdk:17-alpine
VOLUME /tmp
COPY target/*.jar app.jar
EXPOSE 9001
ENTRYPOINT ["java","-jar","/app.jar"]
