# Multi-stage build for Tag Taxonomy Backend

# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /build

# Copy gradle wrapper and settings
COPY gradle ./gradle
COPY gradlew gradlew.bat settings.gradle.kts build.gradle.kts ./

# Copy source code
COPY app ./app
COPY api ./api
COPY service ./service

# Build the application
RUN chmod +x gradlew && \
    ./gradlew clean build -x test --configure-on-demand

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install wait-for-it script
COPY --from=builder /build/app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
