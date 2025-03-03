FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["nohup", "java", "-jar", "/app/app.jar", "> /app/nohup.out", "2>&1"]