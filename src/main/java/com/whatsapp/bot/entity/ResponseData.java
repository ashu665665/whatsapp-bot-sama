package com.whatsapp.bot.entity;

import java.util.List;
import lombok.Data;

@Data
public class ResponseData {
    private String vua;
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
    private List<String> files;
    private String status;
    private String _id;
    private String filingDate;
    private String complaintId;
    private int __v;
}
