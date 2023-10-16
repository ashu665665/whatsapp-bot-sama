FROM maven:3.6.3-openjdk-11

ARG SPRING_PROFILE=dev

# Create the application directory
WORKDIR /app

# Copy the source code into the container
COPY . .

# Check if secrets.env exists and set environment variables if it does
RUN --mount=type=secret,id=secrets_env,dst=/etc/secrets/secrets.env cat /etc/secrets/secrets.env

# Build the application with the active profile
RUN mvn clean install -Dspring.profiles.active=${SPRING_PROFILE}

CMD ["java", "-jar", "target/bot-0.0.1-SNAPSHOT.jar"]
