package ru.ibs.practice.framework.manager.ui;

import lombok.Getter;
import ru.ibs.practice.framework.manager.common.PropertiesManager;

@Getter
public class SeleniumManager {
    private String baseUrl;
    private String chromeDriver;
    private String chromeDriverPath;
    private String chromeBrowserPath;

    private static SeleniumManager INSTANCE;
    private static final PropertiesManager properties = PropertiesManager.getPropertiesManager();

    private SeleniumManager() {
        loadProperties();
    }


    public static SeleniumManager getSeleniumManager() {
        if (INSTANCE == null) {
            INSTANCE = new SeleniumManager();
        }
        return INSTANCE;
    }

    private void loadProperties() {
        baseUrl = properties.get("base.url");
        chromeDriver = properties.get("chrome.driver");
        chromeDriverPath = properties.get("chrome.driver.path");
        chromeBrowserPath = properties.get("browser.chrome.macos");
    }
}