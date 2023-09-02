package ru.ibs.practice.tests.steps;

import io.cucumber.java.ru.И;
import ru.ibs.practice.tests.hooks.WebHooks;
import ru.ibs.practice.tests.ui.pages.FoodPage;
import ru.ibs.practice.tests.ui.pages.HomePage;

public class SeleniumPracticeSteps {
    private final HomePage homePage = new HomePage(WebHooks.getDriver());
    private final FoodPage foodPage = new FoodPage(WebHooks.getDriver());

    @И("Открытие страницы по адресу: {string}")
    public void openHomePage(String url) {
        homePage.open(url);
    }

    @И("Переход на вкладку 'Песочница' -> 'Товары'")
    public void navigateToFoodPage() {
        homePage.navigateToFoodPage();
    }

    @И("Нажать кнопку 'Добавить'")
    public void clickAddProductButton() {
        foodPage.clickAddProductButton();
    }

    @И("Ввести в поле 'Наименование' название {string} на кириллице")
    public void inputProductName(String productName) {
        foodPage.inputProductName(productName);
    }

    @И("Выбрать в выпадающем списке 'Тип' значение {string}")
    public void selectType(String type) {
        if (type.equals("Овощ")) {
            foodPage.selectTypeOfVegetable();
        } else if (type.equals("Фрукт")) {
            foodPage.selectTypeOfFruit();
        }
    }

    @И("Установить флажок 'Экзотический' в положение {string}")
    public void setExoticCheckBoxTo(String value) {
        if (value.equalsIgnoreCase("true")) {
            foodPage.setExoticCheckBoxToTrue();
        } else if (value.equalsIgnoreCase("false")) {
            foodPage.setExoticCheckBoxToFalse();
        }
    }

    @И("Нажать кнопку 'Сохранить'")
    public void clickSaveButton() {
        foodPage.clickSaveButton();
    }

    @И("Проверить, что овощ {string} добавлен в конец таблицы")
    public void checkVegetableToEndTable(String vegetable) {
        foodPage.checkVegetableToEndTable(vegetable);
    }

    @И("Проверить, что фрукт {string} добавлен в конец таблицы")
    public void checkFruitToEndTable(String fruit) {
        foodPage.checkFruitToEndTable(fruit);
    }
}