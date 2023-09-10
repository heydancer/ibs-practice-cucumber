package ru.ibs.practice.framework.page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {
    @FindBy(xpath = "//a[@id='navbarDropdown']")
    private WebElement sandboxDropdownMenu;
    @FindBy(xpath = "//a[@class='dropdown-item']")
    private List<WebElement> itemsSandboxDropdownMenu;

    public HomePage clickOnDropdownMenu() {
        sandboxDropdownMenu.click();
        return this;
    }

    public FoodPage selectBtnInDropdownMenuByText(String buttonName) {
        for (WebElement button : itemsSandboxDropdownMenu) {
            if (button.getText().contains(buttonName)) {
                button.click();
                return pageManager.getFoodPage();
            }
        }

        Assertions.fail(
                String.format("Кнопка: %s не найдена в выпадающем списке навигационной панели", buttonName));

        return pageManager.getFoodPage();
    }
}