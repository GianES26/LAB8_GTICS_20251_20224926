FROM openjdk:25-ea-17-jdk
VOLUME /tmp
EXPOSE 8008
ADD ./target/Laboratorio6-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]