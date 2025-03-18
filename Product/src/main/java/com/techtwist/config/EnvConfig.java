package com.techtwist.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvConfig {
    private static Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = EnvConfig.class.getClassLoader().getResourceAsStream(".env");
            if (inputStream != null) {
                properties.load(inputStream);
                inputStream.close();
            } else {
                System.err.println("Environment file config.env not found in resources.");
            }
        } catch (IOException e) {
            System.err.println("Error loading environment file: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return properties.getProperty(key, ""); // Default to empty if key not found
    }
}
