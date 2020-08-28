FROM openjdk:8-jdk-alpine
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/anonapp-backend-1.0-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/anonapp-backend-1.0-SNAPSHOT.jar"]