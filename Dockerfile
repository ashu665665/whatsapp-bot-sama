# Use an official OpenJDK image as a parent image for the build stage
FROM openjdk:11-jre-slim AS build

# Set the working directory to /app
WORKDIR /app

# Copy the Maven build files to the container
COPY pom.xml .
COPY src src

# Build the JAR file using Maven
RUN ./mvnw package

# Use an official OpenJDK runtime image as a parent image for the final stage
FROM openjdk:11-jre-slim

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/bot-0.0.1-SNAPSHOT.jar /bot-0.0.1-SNAPSHOT.jar
# Copy the secret.env file into the container
COPY /etc/secrets/secrets.env /app/secrets.env

# Expose the port your application runs on
EXPOSE 8080

# Run the application with the secrets
CMD ["java", "-jar", "your-application.jar"]
