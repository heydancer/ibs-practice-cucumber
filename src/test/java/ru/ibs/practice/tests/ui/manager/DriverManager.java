package ru.ibs.practice.tests.ui.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.ibs.practice.config.SeleniumConfig;

import java.time.Duration;

public class DriverManager {

    public static WebDriver initDriver() {
        SeleniumConfig config = new SeleniumConfig();
        ChromeOptions options = new ChromeOptions();

        System.setProperty(config.getChromeDriver(), config.getChromeDriverPath());
        options.setBinary(config.getChromeBrowserPath());

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver;
    }
}
