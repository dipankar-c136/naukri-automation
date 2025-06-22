package com.naukri.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;
    private final String propertyFilePath = "src/main/resources/config.properties";

    public ConfigReader() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(propertyFilePath)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getNaukriUrl() {
        return getProperty("naukri.url");
    }

    public String getBrowserType() {
        return getProperty("browser.type");
    }

    // Add more methods to retrieve other configuration properties as needed
}