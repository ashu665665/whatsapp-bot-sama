package com.whatsapp.bot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TwilioMessage {
    private String Body;
    private String From;
    private String To;
    private String SmsMessageSid;
    private int NumMedia;
    private String ProfileName;
    private String SmsSid;
    private String WaId;
    private String SmsStatus;
    private int NumSegments;
    private int ReferralNumMedia;
    private String MessageSid;
    private String AccountSid;
    private String ApiVersion;
    private String OriginalRepliedMessageSender;
    private String OriginalRepliedMessageSid;
}
