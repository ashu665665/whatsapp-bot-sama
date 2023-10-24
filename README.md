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

1. Scan the QR code to add it to the sandbox group.
   ![QR Code](data:image/svg+xml;utf8,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%2033%2033%22%20shape-rendering%3D%22crispEdges%22%3E%3Cpath%20fill%3D%22%23ffffff%22%20d%3D%22M0%200h33v33H0z%22%2F%3E%3Cpath%20stroke%3D%22%23000000%22%20d%3D%22M0%200.5h7m1%200h2m2%200h6m1%200h1m3%200h2m1%200h7M0%201.5h1m5%200h1m1%200h1m2%200h3m1%200h1m2%200h1m1%200h5m1%200h1m5%200h1M0%202.5h1m1%200h3m1%200h1m2%200h2m1%200h1m4%200h1m1%200h1m1%200h4m1%200h1m1%200h3m1%200h1M0%203.5h1m1%200h3m1%200h1m1%200h1m3%200h1m1%200h3m2%200h6m1%200h1m1%200h3m1%200h1M0%204.5h1m1%200h3m1%200h1m2%200h1m1%200h4m2%200h1m2%200h2m1%200h2m1%200h1m1%200h3m1%200h1M0%205.5h1m5%200h1m4%200h1m2%200h1m4%200h5m2%200h1m5%200h1M0%206.5h7m1%200h1m1%200h1m1%200h1m1%200h1m1%200h1m1%200h1m1%200h1m1%200h1m1%200h1m1%200h7M8%207.5h4m1%200h2m2%200h1m2%200h1m1%200h3M0%208.5h1m1%200h2m1%200h3m2%200h2m1%200h2m2%200h3m2%200h1m3%200h1m2%200h1m1%200h2M1%209.5h1m1%200h1m1%200h1m1%200h2m2%200h1m2%200h3m3%200h1m1%200h5m2%200h2m1%200h1M2%2010.5h1m1%200h1m1%200h3m2%200h1m2%200h2m1%200h1m1%200h3m1%200h2m2%200h3m1%200h2M2%2011.5h2m5%200h3m1%200h4m1%200h1m3%200h1m1%200h2m1%200h1m1%200h1m1%200h2M0%2012.5h1m1%200h1m1%200h3m1%200h1m1%200h1m7%200h2m1%200h1m2%200h2m1%200h3m2%200h1M1%2013.5h1m1%200h1m1%200h1m1%200h1m1%200h3m1%200h1m2%200h1m1%200h1m5%200h5m2%200h1M2%2014.5h1m3%200h1m1%200h4m3%200h4m3%200h2M0%2015.5h1m2%200h2m2%200h1m2%200h1m1%200h2m2%200h6m1%200h5m1%200h2M0%2016.5h1m2%200h8m1%200h2m1%200h2m1%200h4m1%200h1m1%200h2m1%200h3M1%2017.5h1m1%200h3m2%200h3m1%200h2m1%200h1m1%200h1m1%200h8m1%200h2m1%200h1M2%2018.5h1m3%200h2m4%200h2m1%200h1m1%200h1m4%200h1m3%200h1m1%200h1m1%200h2M0%2019.5h1m4%200h1m1%200h2m2%200h1m2%200h1m5%200h2m1%200h2m2%200h2m3%200h1M0%2020.5h2m1%200h5m1%200h1m1%200h2m1%200h2m2%200h1m1%200h1m2%200h1m2%200h1m1%200h3m1%200h1M0%2021.5h1m1%200h1m2%200h1m1%200h1m1%200h2m1%200h2m1%200h1m1%200h2m9%200h1m1%200h1m1%200h1M2%2022.5h1m1%200h3m4%200h3m2%200h2m4%200h1m3%200h1m1%200h2m1%200h2M1%2023.5h2m4%200h1m2%200h1m3%200h1m1%200h2m1%200h1m1%200h2m4%200h1m1%200h4M0%2024.5h1m3%200h3m1%200h1m1%200h3m2%200h1m4%200h2m2%200h5m1%200h3M8%2025.5h1m1%200h1m1%200h2m1%200h3m2%200h1m2%200h2m3%200h2m1%200h2M0%2026.5h7m1%200h1m3%200h1m2%200h2m2%200h1m2%200h3m1%200h1m1%200h1m3%200h1M0%2027.5h1m5%200h1m1%200h2m1%200h2m2%200h1m3%200h1m1%200h4m3%200h1m1%200h1m1%200h1M0%2028.5h1m1%200h3m1%200h1m2%200h2m1%200h3m2%200h1m1%200h2m2%200h6m1%200h1m1%200h1M0%2029.5h1m1%200h3m1%200h1m1%200h2m2%200h1m1%200h1m2%200h5m2%200h2m3%200h1m1%200h1M0%2030.5h1m1%200h3m1%200h1m1%200h1m1%200h3m4%200h3m1%200h3m2%200h2M0%2031.5h1m5%200h1m3%200h1m1%200h5m2%200h1m1%200h2m2%200h2m1%200h1m1%200h1m1%200h1M0%2032.5h7m1%200h2m2%200h2m1%200h1m1%200h2m3%200h5m2%200h1%22%2F%3E%3C%2Fsvg%3E)

2. Once added, just message "Hi@Sama" to the same number to initialize a conversation.

**Note:** A user added to the sandbox will be automatically removed after 72 hours if no messages are sent.