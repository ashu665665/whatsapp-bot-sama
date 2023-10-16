FROM maven:3.6.3-openjdk-11

ARG SPRING_PROFILE=dev
ARG TWILIO_ACCOUNT_SID
ARG TWILIO_AUTH_TOKEN

# Create the application directory
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the application with the active profile
RUN mvn clean install -Dspring.profiles.active=${SPRING_PROFILE}

CMD ["java", "-jar", "target/bot-0.0.1-SNAPSHOT.jar"]
