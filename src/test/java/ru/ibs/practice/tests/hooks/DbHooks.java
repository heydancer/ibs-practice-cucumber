package ru.ibs.practice.tests.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import ru.ibs.practice.framework.manager.db.DataBaseManager;
import ru.ibs.practice.framework.manager.db.DataSourceManager;
import ru.ibs.practice.framework.manager.db.impl.JDBCManager;
import ru.ibs.practice.framework.manager.db.impl.SpringJDBCManager;

import java.sql.Connection;
import java.sql.SQLException;

public class DbHooks {
    private static final DataSourceManager dataSourceManager = DataSourceManager.getDataSourceManager();
    private static DataBaseManager dataBaseManager;
    private static Connection connection;

    public static DataBaseManager getDataBaseManager() {
        return dataBaseManager;
    }

    @Before("@springJdbc")
    public void initSpringJDBCManager() {
        dataBaseManager = new SpringJDBCManager(dataSourceManager.getDataSource());
    }

    @Before("@jdbc")
    public void initJDBCManager() {
        try {
            connection = dataSourceManager.getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось установить соединение с базой данных");
        }

        dataBaseManager = new JDBCManager(connection);
    }

    @After("@jdbc")
    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при закрытии соединения с базой данных");
            }
        }
    }
}