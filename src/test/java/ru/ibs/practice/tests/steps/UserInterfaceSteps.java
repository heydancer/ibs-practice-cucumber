package ru.ibs.practice.tests.steps;

import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.И;
import ru.ibs.practice.framework.manager.ui.PageManager;

public class UserInterfaceSteps {
    private final PageManager pageManager = PageManager.getPageManager();

    @И("Переход на вкладку 'Песочница' -> {string}")
    public void navigateToFoodPage(String btnFood) {
        pageManager.getHomePage()
                .clickOnDropdownMenu()
                .selectBtnInDropdownMenuByText(btnFood);
    }

    @И("Нажать кнопку 'Добавить'")
    public void clickAddProductButton() {
        pageManager.getFoodPage()
                .clickAddProductButton();
    }

    @И("Ввести в поле 'Наименование' название {string} на кириллице")
    public void inputProductName(String productName) {
        pageManager.getFoodPage()
                .inputProductName(productName);
    }

    @И("Выбрать в выпадающем списке 'Тип' значение {string}")
    public void selectType(String type) {
        pageManager.getFoodPage()
                .selectTypeByText(type);
    }

    @ParameterType(value = "true|True|TRUE|false|False|FALSE")
    public Boolean booleanValue(String value) {
        return Boolean.valueOf(value);
    }

    @И("Установить флажок 'Экзотический' в положение {booleanValue}")
    public void setExoticCheckBoxTo(Boolean value) {
        pageManager.getFoodPage()
                .setExoticCheckBox(value);
    }

    @И("Нажать кнопку 'Сохранить'")
    public void clickSaveButton() {
        pageManager.getFoodPage()
                .clickSaveButton();
    }

    @И("Проверить, что продукт: Наименование - {string}, Тип - {string}, Экзотик статус - {booleanValue} добавлен в конец таблицы")
    public void checkFoodToEndTable(String productName, String typeOfProduct, Boolean exoticStatus) {
        pageManager.getFoodPage()
                .checkFoodToEndTable(productName, typeOfProduct, exoticStatus);
    }
}