package ru.ibs.practice.tests.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.ibs.practice.framework.manager.common.PropertiesManager;
import ru.ibs.practice.framework.manager.ui.DriverManager;

public class WebHooks {
    private final PropertiesManager propertiesManager = PropertiesManager.getPropertiesManager();
    private final DriverManager driverManager = DriverManager.getDriverManager();

    @Before("@ui")
    public void initDriver() {
        driverManager.getDriver().get(propertiesManager.get("base.url"));
    }

    @After("@last")
    public void closeDriver() {
        driverManager.quitDriver();
    }
}