package com.whatsapp.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(new ClassPathResource("app.properties"));
        configurer.setIgnoreResourceNotFound(false);
        return configurer;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
