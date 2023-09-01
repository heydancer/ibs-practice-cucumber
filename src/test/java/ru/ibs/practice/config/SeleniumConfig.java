package ru.ibs.practice.config;

import lombok.Getter;

@Getter
public class SeleniumConfig {
    private String baseUrl;
    private String chromeDriver;
    private String chromeDriverPath;
    private String chromeBrowserPath;
    private final PropertiesConfig propConfig;

    public SeleniumConfig() {
        propConfig = new PropertiesConfig();
        loadProperties();
    }

    private void loadProperties() {
        baseUrl = propConfig.get("base.url");
        chromeDriver = propConfig.get("chrome.driver");
        chromeDriverPath = propConfig.get("chrome.driver.path");
        chromeBrowserPath = propConfig.get("browser.chrome.macos");
    }
}

