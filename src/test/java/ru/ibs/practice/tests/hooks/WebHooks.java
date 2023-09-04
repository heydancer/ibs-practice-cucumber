package ru.ibs.practice.tests.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import ru.ibs.practice.tests.ui.manager.DriverManager;

public class WebHooks {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    @Before("@ui")
    public void initDriver() {
        driver = DriverManager.getDriver();

    }

    @After("@ui")
    public void closeDriver() {
        DriverManager.quiteDriver();
    }
}