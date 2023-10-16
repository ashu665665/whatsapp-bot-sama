FROM maven:3.6.3-openjdk-11

# Create the application directory
WORKDIR /app

# Copy the source code into the container
COPY . .

# Check if secrets.env exists and set environment variables if it does
RUN if [ -f /etc/secrets/secrets.env ]; then export $(cat /etc/secrets/secrets.env | xargs); fi

# Build the application with the active profile
RUN mvn clean install

CMD ["java", "-jar", "target/bot-0.0.1-SNAPSHOT.jar"]
