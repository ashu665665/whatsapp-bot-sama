package com.whatsapp.bot.config;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.util.List;

public class SecretsEnvPropertySourceLoader implements PropertySourceLoader {

    private final PropertiesPropertySourceLoader propertiesLoader = new PropertiesPropertySourceLoader();

    @Override
    public String[] getFileExtensions() {
        return new String[] { "env" };
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        if ("secrets".equals(name) && resource.getFilename().equals("secrets.env")) {
            // Customize loading logic for the secrets.env file here
            return propertiesLoader.load(name, resource);
        } else {
            return null;
        }
    }
}

