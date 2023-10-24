# Use an official OpenJDK runtime image as the parent image for the final stage
FROM openjdk:11-jre-slim

# Set the working directory to /app
VOLUME /tmp

# Copy the JAR file from the build stage
ADD target/bot-0.0.1-SNAPSHOT.jar /app.jar

# Specify the command to run your Spring Boot application with the environment file
ENTRYPOINT ["java", "-jar", "app.jar"]
