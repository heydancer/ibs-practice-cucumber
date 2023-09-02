package ru.ibs.practice.tests.steps;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ru.И;
import org.junit.jupiter.api.DisplayName;
import ru.ibs.practice.config.DataSourceConfig;
import ru.ibs.practice.tests.db.service.DataBaseService;
import ru.ibs.practice.tests.db.service.JdbcDataBaseService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@DisplayName("Проверка добавления товара в БД на чисто JDBC")
public class JdbcPracticeSteps {
    private static Connection connection;
    private static DataBaseService service;

    @BeforeAll
    public static void setUp() {
        DataSourceConfig config = new DataSourceConfig();
        DataSource dataSource = config.getDataSource();

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с базой данных");
        }

        service = new JdbcDataBaseService(connection);
    }

    @AfterAll
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при закрытии соединения с базой данных");
            }
        }
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