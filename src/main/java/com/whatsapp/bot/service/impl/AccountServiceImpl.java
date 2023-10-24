package com.whatsapp.bot.service.impl;

import com.whatsapp.bot.entity.ComplaintResponse;
import com.whatsapp.bot.entity.FileCaseRequest;
import com.whatsapp.bot.entity.ResponseData;
import com.whatsapp.bot.service.IAccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Log4j2
public class AccountServiceImpl implements IAccountService {

    @Value("${api.base.url}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate;

    public AccountServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseData caseFile(FileCaseRequest fileCaseRequest) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiBaseUrl)
                    .path("/api/complaint/create")
                    .queryParam("vua", "")
                    .queryParam("organizationUid", "O8MLRR28B888DFE5")
                    .queryParam("complaintCategory", fileCaseRequest.getComplaintCategory())
                    .queryParam("complaintDescription", fileCaseRequest.getComplaintDescription())
                    .queryParam("complainantName", fileCaseRequest.getComplainantName())
                    .queryParam("complainantEmail", fileCaseRequest.getComplainantEmail())
                    .queryParam("complainantPhone", fileCaseRequest.getComplainantPhone())
                    .queryParam("complainantAddress", fileCaseRequest.getComplainantAddress())
                    .queryParam("respondentName", fileCaseRequest.getRespondentName())
                    .queryParam("respondentEmail", fileCaseRequest.getRespondentEmail())
                    .queryParam("respondentPhone", fileCaseRequest.getRespondentPhone())
                    .queryParam("respondentAddress", fileCaseRequest.getRespondentAddress())
                    .toUriString();

            ResponseEntity<ResponseData> response = restTemplate.postForEntity(url, null, ResponseData.class);
            log.info(response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error while filing a complaint", e);
            return null;
        }
    }

    @Override
    public ResponseData getCase(String caseNumber) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(apiBaseUrl)
                    .path("/api/complaint/" + caseNumber)
                    .toUriString();

            ResponseEntity<ComplaintResponse> response = restTemplate.getForEntity(url, ComplaintResponse.class);
            log.info(response);
            return response.getBody().getData();
        } catch (Exception e) {
            log.error("Error while fetching case details", e);
            return null;
        }
    }
}
