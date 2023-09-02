package ru.ibs.practice.tests.steps;

import io.cucumber.java.ru.И;
import ru.ibs.practice.config.DataSourceConfig;
import ru.ibs.practice.tests.db.service.DataBaseService;
import ru.ibs.practice.tests.db.service.SpringDataBaseService;
public class SpringJdbcPracticeSteps {
    private final DataBaseService service = new SpringDataBaseService(new DataSourceConfig().getDataSource());

    @И("Spring. Проверить наличие таблицы FOOD в схеме БД")
    public void checkTableInDB() {
        service.checkTable();
    }

    @И("Spring. Выполнить SQL-запрос на добавление нового товара в таблицу FOOD. Товар: {string}, тип: {string}, экзотически: {int}")
    public void addNewProductInDB(String product, String type, int exotic) {
        service.addProduct(product, type, exotic);
    }

    @И("Spring. Выполнить SQL-запрос для получения списка всех товаров таблицы FOOD")
    public void getAllProduct() {
        service.findAll();
    }

    @И("Spring. Выполнить SQL-запрос на удаление товара из таблицы FOOD")
    public void removeProductFromDB() {
        service.removeProduct();
    }
}