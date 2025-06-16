FROM gradle:8-jdk21-alpine AS Builder

WORKDIR /app
COPY gradlew /app/
RUN chmod +x gradlew
COPY gradle /app/gradle/
COPY settings.gradle /app/
COPY build.gradle /app/
COPY src /app/src/

RUN ./gradlew build -x test

FROM openjdk:21

WORKDIR /app
COPY --from=Builder /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
