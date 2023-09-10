package ru.ibs.practice.framework.page;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Slf4j
public class FoodPage extends BasePage {
    @FindBy(xpath = "//button[text()='Добавить']")
    private WebElement addProductBtn;
    @FindBy(xpath = "//div[@class='modal-content']")
    private WebElement addProductWin;
    @FindBy(xpath = "//label[text()='Наименование']")
    private WebElement productNameLabel;
    @FindBy(xpath = "//label[text()='Тип']")
    private WebElement productTypeLabel;
    @FindBy(xpath = "//input[@id='exotic']")
    private WebElement exoticCheckbox;
    @FindBy(xpath = "//button[@id='save']")
    private WebElement saveProductBtn;
    @FindBy(xpath = "//button[@class='close']")
    private WebElement closeAddProdWin;
    @FindBy(xpath = "//input[@id='name']")
    private WebElement inputFieldName;
    @FindBy(xpath = "//option")
    private List<WebElement> foodTypeBtnList;
    @FindBy(xpath = "//tbody/tr[last()]")
    private WebElement lastProductInTable;

    @Step("Нажать кнопку 'Добавить'")
    public FoodPage clickAddProductButton() {
        log.info("Нажатие кнопки 'Добавить'");

        addProductBtn.click();
        waitElementIsVisible(addProductWin);

        return checkAddProductWin();
    }

    @Step("Ввести в поле название {productName} на кириллице")
    public FoodPage inputProductName(String productName) {
        log.info("Ввод в поле название овоща на кириллице '{}'", productName);

        inputFieldName.sendKeys(productName);

        return checkEnteredProductName(productName, inputFieldName);
    }

    @Step("Выбрать в выпадающем списке {foodType}")
    public FoodPage selectTypeByText(String foodType) {
        log.info("Выбор в выпадающем списке 'Тип' значение 'Овощ'");

        for (WebElement button : foodTypeBtnList) {
            if (button.getText().contains(foodType)) {
                button.click();

                return checkType(foodType, button);
            }
        }

        Assertions.fail(
                String.format("Тип продукта: %s не найден в выпадающем списке тип модального окна", foodType));

        return this;
    }

    @Step("Установить состояние чекбокса в {exoticStatus}}")
    public FoodPage setExoticCheckBox(boolean exoticStatus) {
        log.info("Установка состояние чекбокса в {}", exoticStatus);

        if (exoticStatus && !exoticCheckbox.isSelected()) {
            exoticCheckbox.click();
        } else if (!exoticStatus && exoticCheckbox.isSelected()) {
            exoticCheckbox.click();
        }

        return checkCheckboxState(exoticStatus, exoticCheckbox);
    }

    @Step("Нажать кнопку 'Сохранить'")
    public FoodPage clickSaveButton() {
        log.info("Нажатие кнопки 'Сохранить'");

        saveProductBtn.click();
        waitElementIsInvisible(addProductWin);

        return checkCloseAddWin();
    }

    private FoodPage checkAddProductWin() {
        log.info("Проверка открытия окна 'Добавление товара'");

        Assertions.assertTrue(addProductWin.isDisplayed(),
                "Не отображается окно 'Добавление товара'");
        Assertions.assertTrue(productNameLabel.isDisplayed(),
                "Не отображается поле 'Наименование'");
        Assertions.assertTrue(productTypeLabel.isDisplayed(),
                "Не отображается поле 'Тип'");
        Assertions.assertTrue(exoticCheckbox.isDisplayed(),
                "Не отображается checkbox 'Экзотический'");
        Assertions.assertTrue(saveProductBtn.isDisplayed(),
                "Не отображается кнопка 'Сохранить'");
        Assertions.assertTrue(closeAddProdWin.isDisplayed(),
                "Не отображается кнопка 'x' -> 'закрыть окно'");

        return this;
    }

    private FoodPage checkCloseAddWin() {
        log.info("Проверка закрытия окна 'Добавление товара'");

        Assertions.assertFalse(addProductWin.isDisplayed(),
                "Окно 'Добавление товара' не должно отображаться");

        return this;
    }

    private FoodPage checkType(String typeOfProduct, WebElement element) {
        log.info("Проверка выбранного значения: {} на соответствие типу: {}",
                element.getText(), typeOfProduct);

        Assertions.assertEquals(typeOfProduct, element.getText(),
                String.format("Тип продукта: %s не соответствует типу: %s", typeOfProduct, element.getText()));

        return this;
    }

    private FoodPage checkCheckboxState(boolean exoticState, WebElement element) {
        log.info("Проверка состояния чекбокса");

        if (exoticState) {
            Assertions.assertTrue(element.isSelected(),
                    String.format("Чекбокс должен быть в состоянии %b", exoticState));
        } else {
            Assertions.assertFalse(element.isSelected(),
                    String.format("Чекбокс должен быть в состоянии %b", exoticState));
        }

        return this;
    }

    private FoodPage checkEnteredProductName(String productName, WebElement element) {
        log.info("Проверка введенного значения");

        Assertions.assertEquals(productName, element.getAttribute("value"),
                String.format("Введенное наименование не соответствует наименованию '%s'", productName));

        return this;
    }

    public FoodPage checkFoodToEndTable(String lastProductName, String typeOfProduct, boolean exoticState) {
        log.info("Проверка добавления продукта: {} в конец таблицы", lastProductName);

        Assertions.assertTrue(lastProductInTable.getText().contains(lastProductName),
                String.format("Последний продукт в таблице не соответствует наименованию '%s'", lastProductName));

        Assertions.assertTrue(lastProductInTable.getText().contains(typeOfProduct),
                String.format("Последний продукт в таблице не соответствует типу '%s'", typeOfProduct));

        Assertions.assertTrue(lastProductInTable.getText().contains(String.valueOf(exoticState)),
                String.format("Последний продукт в таблице должен быть в состоянии: '%b'", exoticState));

        return this;
    }
}