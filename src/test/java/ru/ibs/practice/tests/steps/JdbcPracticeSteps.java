package ru.ibs.practice.tests.steps;

import io.cucumber.java.ru.И;
import ru.ibs.practice.tests.db.service.DataBaseService;
import ru.ibs.practice.tests.db.service.JdbcDataBaseService;
import ru.ibs.practice.tests.hooks.DbHooks;

public class JdbcPracticeSteps {
    private final DataBaseService service = new JdbcDataBaseService(DbHooks.getConnection());

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