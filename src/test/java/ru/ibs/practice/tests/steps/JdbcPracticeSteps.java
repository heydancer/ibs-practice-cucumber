package ru.ibs.practice.tests.steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ru.И;
import ru.ibs.practice.tests.db.service.DataBaseService;
import ru.ibs.practice.tests.db.service.JdbcDataBaseService;
import ru.ibs.practice.tests.hooks.DbHooks;

import java.sql.Connection;
public class JdbcPracticeSteps {
    private static DataBaseService service;

    @BeforeAll
    public static void setUp() {
        DbHooks.setUp();
        Connection connection = DbHooks.getConnection();
        service = new JdbcDataBaseService(connection);
    }

    @AfterAll
    public static void close() {
        DbHooks.close();
    }

    @И("Проверить наличие таблицы FOOD в схеме БД")
    public void checkTableInDB() {
        service.checkTable();
    }

    @И("Выполнить SQL-запрос на добавление нового товара в таблицу FOOD. Товар: {string}, тип: {string}, экзотически: {int}")
    public void addNewProductInDB(String product, String type, int exotic) {
        service.addProduct(product, type, exotic);
    }

    @И("Выполнить SQL-запрос для получения списка всех товаров таблицы FOOD")
    public void getAllProduct() {
        service.findAll();
    }

    @И("Выполнить SQL-запрос на удаление товара из таблицы FOOD")
    public void removeProductFromDB() {
        service.removeProduct();
    }
}