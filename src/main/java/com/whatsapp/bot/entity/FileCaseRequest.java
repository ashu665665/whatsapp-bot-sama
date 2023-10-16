package com.whatsapp.bot.entity;

import lombok.Data;

@Data
public class FileCaseRequest {
    private String complaintDescription;
    private String complaintCategory;
    private String complainantName;
    private String complainantEmail;
    private String complainantPhone;
    private String complainantAddress;
    private String respondentName;
    private String respondentEmail;
    private String respondentPhone;
    private String respondentAddress;
}
