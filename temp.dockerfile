FROM eclipse-temurin:25-jre-alpine

WORKDIR /app

ARG JAR_FILE=build/libs/Gikipedia-server-V1-1.0.jar
COPY ${JAR_FILE} app.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-jar", \
    "app.jar"]