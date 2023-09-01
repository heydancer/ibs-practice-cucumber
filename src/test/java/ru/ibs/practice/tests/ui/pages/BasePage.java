package ru.ibs.practice.tests.ui.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public class BasePage {
    protected WebDriver driver;
    private final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String url) {
        log.info("Переходим по адресу: {}", url);

        driver.get(url);
    }

    protected void waitElementIsVisible(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected void waitElementIsInvisible(By by) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}
