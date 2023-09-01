package ru.ibs.practice.tests.ui.general;

import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebDriver;
import ru.ibs.practice.config.SeleniumConfig;
import ru.ibs.practice.tests.ui.manager.DriverManager;
import ru.ibs.practice.tests.ui.pages.FoodPage;
import ru.ibs.practice.tests.ui.pages.HomePage;

public class BaseTestUI {
    protected static WebDriver driver = DriverManager.initDriver();
    protected SeleniumConfig config = new SeleniumConfig();
    protected HomePage homePage = new HomePage(driver);
    protected FoodPage foodPage = new FoodPage(driver);

    @AfterAll
    public static void close() {
        driver.quit();
    }
}