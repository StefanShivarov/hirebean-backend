# Смени 23 на 21 тук
FROM gradle:8.12.0-jdk21 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build -x test --no-daemon


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]