package ru.ibs.practice.tests.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ibs.practice.tests.ui.general.BaseTestUI;

@Owner("Ustiantcev Aleksandr")
@DisplayName("Проверка добавления продуктов UI")
public class SeleniumPracticeTest extends BaseTestUI {
    private static final String NON_EXOTIC_PRODUCT = "Морковь";
    private static final String EXOTIC_PRODUCT = "Дуриан";

    @BeforeEach
    public void precondition() {
        homePage.open(config.getBaseUrl());
        homePage.navigateToFoodPage();

    }

    @Test
    @DisplayName("Добавление овоща, экзотический = false")
    @Description("Проверка возможности добавить продукт с типом 'Овощ' не экзотический")
    public void testAddingNonExoticVegetable() {
        foodPage.clickAddProductButton()
                .inputProductName(NON_EXOTIC_PRODUCT)
                .selectTypeOfVegetable()
                .setExoticCheckBoxToFalse()
                .clickSaveButton()
                .checkVegetableToEndTable(NON_EXOTIC_PRODUCT);
    }

    @Test
    @DisplayName("Добавление овоща, экзотический = true")
    @Description("Проверка возможности добавить продукт с типом 'фрукт' экзотический")
    public void testAddingExoticVegetable() {
        foodPage.clickAddProductButton()
                .inputProductName(EXOTIC_PRODUCT)
                .selectTypeOfFruit()
                .setExoticCheckBoxToTrue()
                .clickSaveButton()
                .checkFruitToEndTable(EXOTIC_PRODUCT);
    }
}
