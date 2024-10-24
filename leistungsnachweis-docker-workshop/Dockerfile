# Step 1: Use an official Gradle image to compile the Spring Boot application
FROM gradle:8.10.2-jdk21 AS builder

# Set the working directory inside the container for build operations
WORKDIR /leistungsnachweis-docker-workshop

# Copy all source code and project files from the host machine to the container
COPY . .

# Build the project and create a shadow JAR without starting the Gradle daemon
RUN gradle clean shadowJar --no-daemon

# Step 2: Use a minimal OpenJDK image to run the application
FROM openjdk:21-jdk-slim

# Set the working directory for running the application
WORKDIR /leistungsnachweis-docker-workshop

# Copy the generated JAR file from the builder stage into the runtime container
COPY --from=builder /leistungsnachweis-docker-workshop/build/libs/*.jar app.jar

# Set the command to run the Spring Boot application using the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
