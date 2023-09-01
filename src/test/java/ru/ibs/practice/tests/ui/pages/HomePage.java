package ru.ibs.practice.tests.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import ru.ibs.practice.tests.ui.util.XPaths;

@Slf4j
public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToFoodPage() {
        log.info("Переход на вкладку 'Песочница' -> 'Товары'");

        driver.findElement(XPaths.NAVBAR_DROPDOWN).click();
        driver.findElement(XPaths.BTN_FOOD_IN_NAVBAR_DROPDOWN).click();
    }
}