FROM openjdk:17
COPY build/libs/*.jar application.jar
ENTRYPOINT ["java", "-jar", "/application.jar"]
EXPOSE 8080