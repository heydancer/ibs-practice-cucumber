package ru.ibs.practice.framework.manager.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverManager {
    private WebDriver driver;
    private static DriverManager INSTANCE;
    private final SeleniumManager selenium = SeleniumManager.getSeleniumManager();

    private DriverManager() {
        initDriver();
    }

    public static DriverManager getDriverManager() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private void initDriver() {
        ChromeOptions options = new ChromeOptions();
        System.setProperty(selenium.getChromeDriver(), selenium.getChromeDriverPath());
        options.setBinary(selenium.getChromeBrowserPath());

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}