package ru.ibs.practice.config;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class DataSourceConfig {
    private final PropertiesConfig propConfig;

    public DataSourceConfig() {
        propConfig = new PropertiesConfig();

    }

    public DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(propConfig.get("db.url"));
        dataSource.setUser(propConfig.get("db.username"));
        dataSource.setPassword(propConfig.get("db.password"));

        return dataSource;
    }
}