package ru.ibs.practice.framework.manager.db;

import org.h2.jdbcx.JdbcDataSource;
import ru.ibs.practice.framework.manager.common.PropertiesManager;

import javax.sql.DataSource;

public class DataSourceManager {
    private static DataSourceManager INSTANCE;
    private static final PropertiesManager properties = PropertiesManager.getPropertiesManager();

    private DataSourceManager() {
        getDataSource();
    }

    public static DataSourceManager getDataSourceManager() {
        if (INSTANCE == null) {
            INSTANCE = new DataSourceManager();
        }
        return INSTANCE;
    }

    public DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(properties.get("db.url"));
        dataSource.setUser(properties.get("db.username"));
        dataSource.setPassword(properties.get("db.password"));

        return dataSource;
    }
}