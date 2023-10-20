package com.whatsapp.bot.config;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.annotation.Bean;

public class MyApplicationInitializer implements ApplicationContextInitializer<GenericApplicationContext> {
    @Bean
    public PropertySourceLoader propertySourceLoader() {
        return new SecretsEnvPropertySourceLoader();
    }
    @Override
    public void initialize(GenericApplicationContext context) {

    }
}

