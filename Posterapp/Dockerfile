# -------------------- BUILD STAGE --------------------
FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

WORKDIR /home/app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean install -DskipTests

# -------------------- RUNTIME STAGE --------------------
FROM alpine:3.21.2

# Install JDK and tzdata
RUN apk update && \
    apk add --no-cache openjdk21 tzdata && \
    adduser -D -u 1000 springboot

# Set timezone
ENV TZ=Asia/Kolkata

# Create application directory
RUN mkdir -p /application && chown -R springboot:springboot /application

USER springboot
WORKDIR /application

# Copy the JAR from the build stage
COPY --from=build /home/app/target/posterapp-0.0.1-SNAPSHOT.jar .

# Run the Spring Boot application
CMD ["java", "-Xms256m", "-Xmx6g", "-Dspring.mvc.task.execution.threadPool.coreSize=50", "-Dspring.mvc.task.execution.threadPool.maxSize=400", "-jar", "posterapp-0.0.1-SNAPSHOT.jar"]
