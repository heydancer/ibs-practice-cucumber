package ru.ibs.practice.framework.manager.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesManager {
    private final Properties properties = new Properties();
    private static PropertiesManager INSTANCE;

    private PropertiesManager() {
        loadProperties();
    }

    public static PropertiesManager getPropertiesManager() {
        if (INSTANCE == null) {
            INSTANCE = new PropertiesManager();
        }
        return INSTANCE;
    }

    private void loadProperties() {
        try {
            InputStream inputStream = Files.newInputStream(
                    Path.of("src/main/resources/config.properties"));

            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}