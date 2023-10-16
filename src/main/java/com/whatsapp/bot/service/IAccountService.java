package com.whatsapp.bot.service;

import com.whatsapp.bot.entity.FileCaseRequest;
import com.whatsapp.bot.entity.ResponseData;

public interface IAccountService {

    public ResponseData caseFile(FileCaseRequest request);
    public ResponseData getCase(String caseNumber);
}
