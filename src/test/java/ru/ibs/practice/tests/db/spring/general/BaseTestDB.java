package ru.ibs.practice.tests.db.spring.general;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.ibs.practice.config.DataSourceConfig;

import javax.sql.DataSource;

public class BaseTestDB {
    private static DataSource dataSource;
    protected JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setUp() {
        dataSource = new DataSourceConfig().getDataSource();
    }

    @BeforeEach
    public void initJdbcTemplate() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
}