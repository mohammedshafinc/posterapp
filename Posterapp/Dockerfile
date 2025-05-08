FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy POM first for dependency resolution
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source files
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21.0.2_13-jre-jammy

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/posterapp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
