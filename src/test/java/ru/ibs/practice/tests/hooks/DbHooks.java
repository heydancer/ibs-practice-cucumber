package ru.ibs.practice.tests.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.ibs.practice.config.DataSourceConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbHooks {
    private static Connection connection;
    private static DataSource dataSource;

    @Before("@db")
    public void setUp() {
        DataSourceConfig config = new DataSourceConfig();
        dataSource = config.getDataSource();

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с базой данных");
        }
    }

    @After("@db")
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при закрытии соединения с базой данных");
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}