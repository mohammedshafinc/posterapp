FROM maven:3.9.4-eclipse-temurin-21 AS build
 
WORKDIR /app
 
# Copy POM first to leverage Docker cache for dependencies
COPY pom.xml .
 
# Download dependencies
RUN mvn dependency:go-offline -B
 
# Copy source code
COPY src ./src
 
# Package the application
RUN mvn clean package -DskipTests
 
# Runtime stage
FROM eclipse-temurin:21-jre-alpine
 
WORKDIR /app
 
# Copy only the JAR file from the build stage
COPY --from=build /app/target/posterapp-0.0.1-SNAPSHOT.jar app.jar
 
EXPOSE 8080
 
ENTRYPOINT ["java", "-jar", "app.jar"]
