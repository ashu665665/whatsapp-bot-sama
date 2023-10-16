package com.whatsapp.bot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController {

    @Value("${spring.application.name:whatsapp-bot}")
    private String applicationName;

    @Value("${spring.application.version:1.0.0}")
    private String applicationVersion;

    private final LocalDateTime applicationStartTime = LocalDateTime.now();

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
        return healthStatus;
    }
}






