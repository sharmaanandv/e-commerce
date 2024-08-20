FROM openjdk:22-jdk
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
