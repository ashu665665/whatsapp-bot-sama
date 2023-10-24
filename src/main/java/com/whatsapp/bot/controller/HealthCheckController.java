package com.whatsapp.bot.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/healthcheck")
@Log4j2
public class HealthCheckController {

    @Value("${spring.application.name:whatsapp-bot}")
    private String applicationName;

    @Value("${spring.application.version:1.0.0}")
    private String applicationVersion;

    @Value("${api.base.url}")
    private String downstreamURL;

    private final LocalDateTime applicationStartTime = LocalDateTime.now();
    private final Map<String,Object> downstreamHC = new LinkedHashMap<>();

    @PostConstruct
    public void setDownstreamHC(){
        String dd_status = "UP";
        String dd_name = "sama-live";
        try {
            URL url = new URL(downstreamURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if(connection.getResponseCode() == 200){
                downstreamHC.put(dd_name, dd_status);
            }else{
                downstreamHC.put(dd_name, "DOWN");
                downstreamHC.put("error", "Response Code Not OK");
            }
        } catch (IOException e) {
            String error = "Error Invoking Sama healthcheck";
            downstreamHC.put(dd_name, "DOWN");
            downstreamHC.put("error", error);
            log.error(error, e.getMessage());
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> healthCheck() {
        Map<String, Object> healthStatus = new LinkedHashMap<>();
        healthStatus.put("applicationName", applicationName);
        healthStatus.put("applicationVersion", applicationVersion);

        // Format the applicationStartTime using DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedStartTime = applicationStartTime.format(formatter);

        healthStatus.put("applicationStartTime", formattedStartTime);
        healthStatus.put("status", "UP");

        healthStatus.put("downstream-health", downstreamHC);
        return healthStatus;
    }
}






