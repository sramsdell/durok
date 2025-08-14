# TODO strip base image down to be lite weight
FROM eclipse-temurin:17

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew

RUN ./gradlew dependencies --write-locks

COPY src ./src

RUN ./gradlew bootJar

FROM eclipse-temurin:17

WORKDIR /app

COPY --from=0 /app/build/libs/*.jar app.jar
COPY --from=0 /app/src/main/resources/version.txt ./src/main/resources/version.txt

EXPOSE 8080

#TODO use a war
ENTRYPOINT ["java", "-jar", "app.jar"]