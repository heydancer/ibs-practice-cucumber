package ru.ibs.practice.tests.hooks;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.WebDriver;
import ru.ibs.practice.tests.ui.manager.DriverManager;

public class WebHooks {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    @BeforeAll
    public static void initDriver() {
        driver = DriverManager.getDriver();

    }

    @AfterAll
    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}