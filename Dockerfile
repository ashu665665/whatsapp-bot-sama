# Use an official Maven image as the parent image for the build stage
FROM maven:3.6.3-jdk-11 AS build

# Set the working directory to /app
WORKDIR /app

# Copy the Maven build files to the container
COPY pom.xml .
COPY src src

# Build the JAR file using Maven without running tests
RUN mvn package -DskipTests

# Use an official OpenJDK runtime image as the parent image for the final stage
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/bot-0.0.1-SNAPSHOT.jar /app/bot-0.0.1-SNAPSHOT.jar

# Copy the secrets environment file into the container at /etc/secrets/secrets.env
COPY /etc/secrets/secrets.env /docker/secrets/


# Specify the command to run your Spring Boot application with the environment file
CMD ["java", "-jar", "bot-0.0.1-SNAPSHOT.jar", "--spring.config.additional-location=file:/docker/secrets/secrets.env"]
