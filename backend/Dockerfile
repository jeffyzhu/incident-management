FROM maven:3.8.5-openjdk-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build files and source code
COPY pom.xml .
COPY src ./src

# Package the Spring Boot application
RUN mvn clean package -DskipTests

# ---

FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the previous step
COPY --from=builder /app/target/Incident-management-0.6-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Set the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]