package ru.ibs.practice.tests.hooks;

import ru.ibs.practice.config.DataSourceConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbHooks {
    private static Connection connection;

    public static void setUp() {
        DataSourceConfig config = new DataSourceConfig();
        DataSource dataSource = config.getDataSource();

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с базой данных");
        }
    }

    public static void close() {
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
}