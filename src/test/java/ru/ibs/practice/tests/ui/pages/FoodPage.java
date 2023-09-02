package ru.ibs.practice.tests.ui.pages;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.ibs.practice.tests.ui.util.XPaths;

@Slf4j
public class FoodPage extends BasePage {
    public FoodPage(WebDriver driver) {
        super(driver);
    }

    public FoodPage clickAddProductButton() {
        log.info("Нажатие кнопки 'Добавить'");

        driver.findElement(XPaths.ADD_PRODUCT_BTN).click();
        waitElementIsVisible(XPaths.ADD_PRODUCT_WIN);

        checkAddProductWin();

        return this;
    }

    public FoodPage inputProductName(String productName) {
        log.info("Ввод в поле 'Наименование' название овоща на кириллице '{}'", productName);

        WebElement nameField = driver.findElement(XPaths.FIELD_NAME);
        nameField.sendKeys(productName);

        checkEnteredProductName(productName, nameField);

        return this;
    }

    public FoodPage selectTypeOfVegetable() {
        log.info("Выбор в выпадающем списке 'Тип' значение 'Овощ'");

        WebElement typeDropdown = driver.findElement(XPaths.BTN_VEGETABLE_IN_TYPE_DROPDOWN);
        typeDropdown.click();

        checkVegetableType(typeDropdown);

        return this;
    }

    public FoodPage setExoticCheckBoxToFalse() {
        log.info("Установка состояние чекбокса 'Экзотический' в false");

        WebElement exoticCheckbox = driver.findElement(XPaths.EXOTIC_CHECKBOX);
        if (exoticCheckbox.isSelected()) {
            exoticCheckbox.click();
        }

        checkCheckBoxFalseState(exoticCheckbox);

        return this;
    }

    public FoodPage setExoticCheckBoxToTrue() {
        log.info("Установка состояние чекбокса 'Экзотический' в true");

        WebElement exoticCheckbox = driver.findElement(XPaths.EXOTIC_CHECKBOX);
        if (!exoticCheckbox.isSelected()) {
            exoticCheckbox.click();
        }

        checkCheckBoxTrueState(exoticCheckbox);

        return this;
    }

    public FoodPage clickSaveButton() {
        log.info("Нажатие кнопки 'Сохранить'");

        driver.findElement(XPaths.SAVE_PRODUCT_BTN).click();
        waitElementIsInvisible(XPaths.ADD_PRODUCT_WIN);

        checkCloseAddWin();

        return this;
    }

    public FoodPage selectTypeOfFruit() {
        log.info("Выбор в выпадающем списке 'Тип' значение 'Фрукт'");

        WebElement typeDropdown = driver.findElement(XPaths.BTN_FRUIT_IN_TYPE_DROPDOWN);
        typeDropdown.click();

        checkFruitType(typeDropdown);

        return this;
    }

    private void checkAddProductWin() {
        log.info("Проверка открытия окна 'Добавление товара'");

        Assertions.assertTrue(driver.findElement(XPaths.ADD_PRODUCT_WIN).isDisplayed(),
                "Не отображается окно 'Добавление товара'");
        Assertions.assertTrue(driver.findElement(XPaths.PRODUCT_NAME_FIELD).isDisplayed(),
                "Не отображается поле 'Наименование'");
        Assertions.assertTrue(driver.findElement(XPaths.PRODUCT_TYPE_FIELD).isDisplayed(),
                "Не отображается поле 'Тип'");
        Assertions.assertTrue(driver.findElement(XPaths.EXOTIC_CHECKBOX).isDisplayed(),
                "Не отображается checkbox 'Экзотический'");
        Assertions.assertTrue(driver.findElement(XPaths.SAVE_PRODUCT_BTN).isDisplayed(),
                "Не отображается кнопка 'Сохранить'");
        Assertions.assertTrue(driver.findElement(XPaths.CLOSE_ADD_PROD_WIN).isDisplayed(),
                "Не отображается кнопка 'x' -> 'закрыть окно'");
    }

    private void checkCloseAddWin() {
        log.info("Проверка закрытия окна 'Добавление товара'");

        Assertions.assertFalse(driver.findElement(XPaths.ADD_PRODUCT_WIN).isDisplayed(),
                "Окно 'Добавление товара' не должно отображаться");
    }

    private void checkFruitType(WebElement element) {
        log.info("Проверка выбранного значения на соответствие типу 'Фрукт'");

        Assertions.assertEquals("FRUIT", element.getAttribute("value"),
                "Атрибут не соответствует типу 'FRUIT'");
        Assertions.assertEquals("Фрукт", element.getText(),
                "Текст не соответствует типу 'Фрукт'");
    }

    private void checkVegetableType(WebElement element) {
        log.info("Проверка выбранного значения на соответствие типу 'Овощ'");

        Assertions.assertEquals("VEGETABLE", element.getAttribute("value"),
                "Атрибут не соответствует типу 'VEGETABLE'");
        Assertions.assertEquals("Овощ", element.getText(),
                "Текст не соответствует типу 'Овощ'");
    }

    private void checkCheckBoxFalseState(WebElement element) {
        log.info("Проверка состояния чекбокса");

        Assertions.assertFalse(element.isSelected(),
                "Чекбокс должен быть в состоянии 'false'");
    }

    private void checkCheckBoxTrueState(WebElement exoticCheckbox) {
        log.info("Проверка состояния чекбокса");

        Assertions.assertTrue(exoticCheckbox.isSelected(),
                "Чекбокс должен быть в состоянии 'true'");
    }

    private void checkEnteredProductName(String productName, WebElement element) {
        log.info("Проверка введенного значения");

        Assertions.assertEquals(productName, element.getAttribute("value"),
                String.format("Введенное наименование не соответствует наименованию '%s'", productName));
    }

    public FoodPage checkFruitToEndTable(String lastProductName) {
        log.info("Проверка добавления наименования фрукта в конец таблицы");

        WebElement lastProduct = driver.findElement(XPaths.LAST_PRODUCT);

        Assertions.assertTrue(lastProduct.getText().contains(lastProductName),
                String.format("Последний товар не соответствует наименованию '%s'", lastProductName));
        Assertions.assertTrue(lastProduct.getText().contains("Фрукт"),
                "Товар не соответствует типу 'Фрукт'");
        Assertions.assertTrue(lastProduct.getText().contains("true"),
                "Товар должен быть в состоянии 'true'");

        return this;
    }

    public FoodPage checkVegetableToEndTable(String lastProductName) {
        log.info("Проверка добавления наименования овоща в конец таблицы");

        WebElement lastProduct = driver.findElement(XPaths.LAST_PRODUCT);

        Assertions.assertTrue(lastProduct.getText().contains(lastProductName),
                String.format("Последний товар не соответствует наименованию '%s'", lastProductName));
        Assertions.assertTrue(lastProduct.getText().contains("Овощ"),
                "Товар не соответствует типу 'Овощ'");
        Assertions.assertTrue(lastProduct.getText().contains("false"),
                "Товар должен быть в состоянии 'false'");

        return this;
    }
}