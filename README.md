# whatsapp-bot-sama
Whatsapp Bot to search and file a case for SAMA.live

## Application Description

This Spring Boot application serves as a WhatsApp bot with various functions, including searching and filing a case on SAMA.live.

## Application Healthcheck

- **Local:** http://localhost:8080/whatsappbot/healthcheck
- **Prod:** https://whatsapp-bot-sama-gspnfxyvma-uc.a.run.app/whatsappbot/healthcheck

## Prerequisites for Running the Application

Before running the application, make sure you have completed the following steps:

1. **Create an Account in Twilio:** You need a Twilio account to send and receive WhatsApp messages.

2. **Get Your Auth Token and Account SID Secrets:** After creating a Twilio account, obtain your Auth Token and Account SID secrets.

3. **Configure Application Properties:** In the `application.properties` file, add the following key-value pairs:

    ```properties
    account_sid=**** (Your Twilio Account SID)
    auth_token=**** (Your Twilio Auth Token)
    ```

## Running the Application Locally

To run the application in a local environment, follow these steps:

1. Build the application:

   ```shell
   mvn clean install
   mvn spring-boot:run

2. Run the application:

    ```shell
    mvn spring-boot:run

3. Initialize the Conversation:

Send a WhatsApp message with "Hi@Sama" to start the conversation.

## Running the Application in a Docker Environment

To run the application in a Docker environment:

1. Build and run the Docker containers:

    ```shell
    docker build -t my-whatsapp-bot .

2. Run the application in container

    ```shell
    docker run -d -p 8080:8080 my-whatsapp-bot

Please note that you should have Docker, Also you have to build the application first before running it in container.

## Testing the Application (Sandbox)

To test the flow, you can use a sandbox account of Twilio:

1. Scan this [QR code](https://drive.google.com/file/d/1PQ7k9KQQXr6LOFhYefGmIJR57dn_o0ZL/view?usp=sharing) to add it to the sandbox group.

2. Once added, just message "Hi@Sama" to the same number to initialize a conversation.

**Note:** A user added to the sandbox will be automatically removed after 72 hours if no messages are sent.