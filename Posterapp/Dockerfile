# Use a very specific Maven and JDK version
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Set JAVA_HOME explicitly
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Verify Java version
RUN java -version && mvn -version

# Copy POM first for dependency resolution
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source files
COPY src ./src

# Build with explicit Java version
RUN mvn clean package -DskipTests -Dmaven.compiler.release=21

# Runtime stage with specific JDK version
FROM eclipse-temurin:21.0.2_13-jre-jammy

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/posterapp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
