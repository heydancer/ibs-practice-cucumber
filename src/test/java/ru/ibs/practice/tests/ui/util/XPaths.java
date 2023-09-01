package ru.ibs.practice.tests.ui.util;

import org.openqa.selenium.By;

/**
 * The class XPaths stores the XPath constants used for locating web elements
 */
public class XPaths {

    /**
     * XPath for the dropdown button in the navigation bar
     */
    public static final By NAVBAR_DROPDOWN = By.xpath("//a[@id='navbarDropdown']");

    /**
     * XPath for the food button in the navbar dropdown
     */
    public static final By BTN_FOOD_IN_NAVBAR_DROPDOWN = By.xpath("//a[@href='/food']");

    /**
     * XPath for the add product button
     */
    public static final By ADD_PRODUCT_BTN = By.xpath("//button[text()='Добавить']");

    /**
     * XPath for the add product window
     */
    public static final By ADD_PRODUCT_WIN = By.xpath("//div[@class='modal-content']");

    /**
     * XPath for the product name field
     */
    public static final By PRODUCT_NAME_FIELD = By.xpath("//label[text()='Наименование']");

    /**
     * XPath for the product type field
     */
    public static final By PRODUCT_TYPE_FIELD = By.xpath("//label[text()='Тип']");

    /**
     * XPath for the exotic checkbox
     */
    public static final By EXOTIC_CHECKBOX = By.xpath("//input[@id='exotic']");

    /**
     * XPath for the save product button
     */
    public static final By SAVE_PRODUCT_BTN = By.xpath("//button[@id='save']");

    /**
     * XPath for the close button in the add product window
     */
    public static final By CLOSE_ADD_PROD_WIN = By.xpath("//button[@class='close']");

    /**
     * XPath for the product name field in the add product window
     */
    public static final By FIELD_NAME = By.xpath("//input[@id='name']");

    /**
     * XPath for the "Овощ" (Vegetable) option in the type dropdown
     */
    public static final By BTN_VEGETABLE_IN_TYPE_DROPDOWN = By.xpath("//option[@value='VEGETABLE']");

    /**
     * XPath for the "Фрукт" (Fruit) option in the type dropdown
     */
    public static final By BTN_FRUIT_IN_TYPE_DROPDOWN = By.xpath("//option[@value='FRUIT']");

    /**
     * XPath for the last product in the table
     */
    public static final By LAST_PRODUCT = By.xpath("//tbody/tr[last()]");
}
