package com.whatsapp.bot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyApplicationConfig {

    @Bean
    public PropertySourceLoader secretsEnvPropertySourceLoader() {
        return new SecretsEnvPropertySourceLoader();
    }

    @Bean
    public ApplicationContextInitializer<GenericApplicationContext> myApplicationContextInitializer() {
        return new MyApplicationInitializer();
    }
}

