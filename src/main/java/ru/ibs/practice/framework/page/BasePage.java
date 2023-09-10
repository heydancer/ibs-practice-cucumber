package ru.ibs.practice.framework.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ibs.practice.framework.manager.ui.DriverManager;
import ru.ibs.practice.framework.manager.ui.PageManager;

import java.time.Duration;

public class BasePage {
    private final DriverManager driverManager = DriverManager.getDriverManager();
    private final WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10));
    protected final PageManager pageManager = PageManager.getPageManager();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected void waitElementIsVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitElementIsInvisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }
}