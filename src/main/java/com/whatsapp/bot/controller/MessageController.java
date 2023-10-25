package com.whatsapp.bot.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.whatsapp.bot.entity.FileCaseRequest;
import com.whatsapp.bot.entity.ResponseData;
import com.whatsapp.bot.entity.TwilioMessage;
import com.whatsapp.bot.entity.UserStateEnum;
import com.whatsapp.bot.service.IAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

@RestController
@RequestMapping("/webhook")
@Log4j2
public class MessageController {

    @Autowired
    private IAccountService accountService;

    @Value("${logoURL}")
    private String logoUrl;

    @Value("${account_sid:#{null}}")
    private String account_sid;

    @Value("${auth_token:#{null}}")
    private String auth_token;

    @Value("${webhook_trigger_msg}")
    private String trigger_msg;

    @Value("${session_timeout_ms:90000}")
    private long sessionTimeoutMs;

    private String account_whatsapp;

    private final Map<String, UserStateEnum> userStates = new HashMap<>();
    private final Map<String, UserStateEnum> fileCase = new HashMap<>();
    private final Map<String, FileCaseRequest> userFileCaseRequests = new HashMap<>();
    private final Map<String, Long> userLastActivityTimestamps = new HashMap<>();

    @PostConstruct
    public void initialize() throws Exception {
        account_sid = account_sid != null ? account_sid : System.getenv("TWILIO_ACCOUNT_SID");
        auth_token = auth_token != null ? auth_token : System.getenv("TWILIO_AUTH_TOKEN");
    }

    @PostMapping("/fromtwilio")
    public void receiveMessage(TwilioMessage message) {
        try {
            account_whatsapp = message.getTo();
            String userFrom = message.getFrom();
            UserStateEnum userState = userStates.computeIfAbsent(userFrom, state -> UserStateEnum.INITIAL);
            userLastActivityTimestamps.put(userFrom, System.currentTimeMillis());

            Consumer<String> sendMessage = msg -> sendMessage(msg, userFrom, account_whatsapp);
            Consumer<String> sendMediaMessage = msg -> sendMessageWithImages(msg, userFrom, account_whatsapp);

            switch (userState) {
                case INITIAL:
                    if (message.getBody().equalsIgnoreCase(trigger_msg)) {
                        userStates.put(userFrom, UserStateEnum.OPTIONS);
                        sendMediaMessage.accept("Hello, " + message.getProfileName() + "!\n" +
                                "Welcome to Sama.live. How can we assist you today?\n\n" +
                                "\u0031\uFE0F\u20E3 Press 1 to file a new case\n" +
                                "\u0032\uFE0F\u20E3 Press 2 to search for an existing case");
                    }else{
                        sendMessage.accept("If you need assistance, simply send 'Hi@Sama'");
                    }
                    break;
                case OPTIONS:
                    handleOptions(message.getBody(), userFrom, sendMessage);
                    break;
                case WAITING_FOR_CASE_NO:
                    userStates.put(userFrom, UserStateEnum.END);
                    handleCaseNo(message.getBody(), sendMessage);
                    cleanupUserSession(userFrom);
                    break;
                case FILE_A_CASE:
                    UserStateEnum fileCaseUserState = fileCase.computeIfAbsent(userFrom, state -> UserStateEnum.FILE_A_CASE_INITIAL);
                    FileCaseRequest fileCaseRequest = userFileCaseRequests.computeIfAbsent(userFrom, k -> new FileCaseRequest());
                    fileCaseRequest.setComplaintDescription(message.getBody());
                    handleFileCase(fileCaseUserState, fileCaseRequest, message, userFrom,sendMessage);
                    break;
            }
        } catch (Exception e) {
            log.error("An error occurred while receiving the message: {}", e.getMessage());
        }
    }

    private void handleOptions(String userMessage, String userFrom, Consumer<String> sendMessage) {
        if ("1".equals(userMessage)) {
            userStates.put(userFrom, UserStateEnum.FILE_A_CASE);
            sendMessage.accept("Please describe your complaint in detail. \uD83D\uDCDD");
        } else if ("2".equals(userMessage)) {
            userStates.put(userFrom, UserStateEnum.WAITING_FOR_CASE_NO);
            sendMessage.accept("Please provide your Complaint ID. \uD83D\uDCDD");
        } else {
            sendMessage.accept("Please choose a valid option. \uD83D\uDE4C");
        }
    }

    private void handleCaseNo(String caseNumber, Consumer<String> sendMessage) {
        ResponseData respData = accountService.getCase(caseNumber);
        String res = "Case ID: " + respData.getComplaintId() + " (" + respData.getStatus() + ")\n" +
                "Complainant: " + respData.getComplainantName() + " vs " + respData.getRespondentName() + "\n" +
                "Description: " + respData.getComplaintDescription();
        sendMessage.accept(res);
        sendMessage.accept("Thanks for using Sama!\u2705\nIf you need assistance, simply send 'Hi@Sama'");
    }

    private void handleFileCase(UserStateEnum fileCaseUserState, FileCaseRequest fileCaseRequest, TwilioMessage message, String userFrom, Consumer<String> sendMessage) {
        switch (fileCaseUserState) {
            case FILE_A_CASE_INITIAL:
                fileCase.put(userFrom, UserStateEnum.FILE_A_CASE_CHOOSE_CATEGORY);
                fileCaseRequest.setComplaintCategory(message.getBody());
                sendMessage.accept("Please choose a category: \n1. Payment Related\n2. Family Related\n3. E-commerce\n4. Insurance\n5. Others (Please Specify)");
                break;
            case FILE_A_CASE_CHOOSE_CATEGORY:
                fileCase.put(userFrom, UserStateEnum.FILE_A_CASE_COMPLAINT_INFO);
                fileCaseRequest.setComplainantEmail(message.getBody());
                fileCaseRequest.setComplainantName(message.getProfileName());
                fileCaseRequest.setComplainantPhone(userFrom.substring(userFrom.length()-10,userFrom.length()));
                sendMessage.accept("Please provide your email address \uD83D\uDCE7");
                break;
            case FILE_A_CASE_COMPLAINT_INFO:
                fileCase.put(userFrom, UserStateEnum.FILE_A_CASE_ADDRESS_INFO);
                fileCaseRequest.setComplainantAddress(message.getBody());
                sendMessage.accept("Please provide your physical address \uD83C\uDFE0");
                break;
            case FILE_A_CASE_ADDRESS_INFO:
                fileCase.put(userFrom, UserStateEnum.FILE_A_CASE_OPPOSITION_PARTY_NAME);
                fileCaseRequest.setRespondentName(message.getBody());
                sendMessage.accept("Please provide the name of the opposition party");
                break;
            case FILE_A_CASE_OPPOSITION_PARTY_NAME:
                fileCase.put(userFrom, UserStateEnum.FILE_A_CASE_OPPOSITION_MAIL_ID);
                fileCaseRequest.setRespondentEmail(message.getBody());
                sendMessage.accept("Please provide the email address of the opposition party");
                break;
            case FILE_A_CASE_OPPOSITION_MAIL_ID:
                fileCase.put(userFrom, UserStateEnum.FILE_A_CASE_OPPOSITION_PHONE);
                fileCaseRequest.setRespondentPhone(message.getBody());
                sendMessage.accept("Please provide the phone number of the opposition party");
                break;
            case FILE_A_CASE_OPPOSITION_PHONE:
                fileCase.put(userFrom, UserStateEnum.FILE_A_CASE_OPPOSITION_ADDRESS);
                fileCaseRequest.setRespondentAddress(message.getBody());
                ResponseData responseData = accountService.caseFile(fileCaseRequest);
                String responseMsg = responseData.getComplaintId() + "\n" +
                        responseData.getComplainantName() + " vs " + responseData.getRespondentName() + "\n" +
                        responseData.getStatus() + "\n" +
                        "Complaint Description: " + responseData.getComplaintDescription() + "\n";
                sendMessage.accept(responseMsg);
                sendMessage.accept("Thanks for using Sama.\u2705\nOur agent will soon reach out to you. \nIf you need assistance, simply send 'Hi@Sama'");
                cleanupUserSession(userFrom);
                break;
        }
    }


    private void removeInactiveSessions(long currentTimeMillis) {
        // Create a list to hold the keys of inactive sessions
        List<String> inactiveSessions = new ArrayList<>();

        // Identify and remove inactive sessions
        userLastActivityTimestamps.forEach((user, lastActivityTimestamp) -> {
            if (currentTimeMillis - lastActivityTimestamp >= sessionTimeoutMs) {
                inactiveSessions.add(user);
            }
        });

        // Remove inactive sessions from all relevant maps
        inactiveSessions.forEach(user -> {
            sendMessage("Your session has been inactive. Goodbye! Send 'Hi@Sama' to start a conversation", user, account_whatsapp);
            userStates.remove(user);
            userFileCaseRequests.remove(user);
            fileCase.remove(user);
            userLastActivityTimestamps.remove(user); // Remove from userLastActivityTimestamps
        });
    }


    @Scheduled(fixedDelayString ="${cleanup_interval_ms:60000}") // Runs every 60 seconds by default
    public void cleanupInactiveSessions() {
        long currentTimeMillis = System.currentTimeMillis();
        removeInactiveSessions(currentTimeMillis);
    }

    private void cleanupUserSession(String userFrom) {
        userStates.remove(userFrom);
        userFileCaseRequests.remove(userFrom);
        fileCase.remove(userFrom);
        userLastActivityTimestamps.remove(userFrom);
    }

    public void sendMessage(String messageToSend, String to, String from) {
        try {
            Twilio.init(account_sid, auth_token);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(from),
                    messageToSend).create();
            log.info("Successfully sent the message");
        } catch (Exception e) {
            log.error("An error occurred while sending the message: {}", e.getMessage());
        }
    }

    public void sendMessageWithImages(String messageToSend, String to, String from) {
        try {
            Twilio.init(account_sid, auth_token);
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(to),
                    new com.twilio.type.PhoneNumber(from),
                    messageToSend).setMediaUrl(logoUrl).create();
            log.info("Successfully sent the message");
        } catch (Exception e) {
            log.error("An error occurred while sending the message: {}", e.getMessage());
        }
    }
}
