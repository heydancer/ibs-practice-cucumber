package ru.ibs.practice.tests.db.jdbc.general;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.ibs.practice.config.DataSourceConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseTestDB {
    protected Connection connection;
    protected PreparedStatement preparedStatement;
    private static DataSource dataSource;

    @BeforeAll
    public static void setUp() {
        dataSource = new DataSourceConfig().getDataSource();
    }

    @BeforeEach
    public void setConnection() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с базой данных");
        }
    }

    @AfterEach
    public void close() {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при закрытии PreparedStatement");
            }
        }

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при закрытии соединения с базой данных");
            }
        }
    }
}