package ru.ibs.practice.framework.manager.db.impl;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.ibs.practice.framework.manager.db.DataBaseManager;
import ru.ibs.practice.framework.model.Food;
import ru.ibs.practice.framework.model.FoodType;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class SpringJDBCManager implements DataBaseManager {
    private static final String SQL_SHOW_TABLES = "SHOW TABLES;";
    private static final String SQL_INSERT_FOOD = "INSERT INTO food(food_name, food_type, food_exotic) VALUES (?, ?, ?);";
    private static final String SQL_SELECT_ALL_FOOD = "SELECT * FROM food;";
    private static final String SQL_SELECT_FOOD = "SELECT * FROM food WHERE food_id = ?;";
    private static final String SQL_DELETE_FOOD = "DELETE FROM food WHERE food_id = ?;";
    private final JdbcTemplate jdbcTemplate;

    public SpringJDBCManager(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Step("Добавлена новая запись в таблицу {productName}, {type}, {exoticStatus}")
    @Override
    public Food addProduct(Food food) {
        log.info("Вставка новой записи в таблицу productName: {}, type: {}, exoticStatus: {}",
                food.getName(), food.getType(), food.isExotic());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int updatedRow = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SQL_INSERT_FOOD, new String[]{"food_id"});
            preparedStatement.setString(1, food.getName());
            preparedStatement.setString(2, food.getType().toString());
            preparedStatement.setBoolean(3, food.isExotic());
            return preparedStatement;
        }, keyHolder);

        if (updatedRow == 0) {
            Assertions.fail(String.format("Товар не был добавлен в таблицу. Проверьте корректность товара: %s", food));
        }

        int foodId = Objects.requireNonNull(keyHolder.getKey()).intValue();

        return Food.builder()
                .id(foodId)
                .name(food.getName())
                .type(food.getType())
                .exotic(food.isExotic())
                .build();
    }

    @Step("Получен список товаров через SQL-запрос")
    @Override
    public List<Food> findAll() {
        log.info("Выполнение SQL-запроса для получения списка всех товаров таблицы 'FOOD'");

        return jdbcTemplate.query(SQL_SELECT_ALL_FOOD, this::mapRow);
    }

    @Step("Выполнен SQL-запроса для получения товара по ID: {foodId}")
    @Override
    public Optional<Food> findById(int foodId) {
        log.info("Выполнение SQL-запроса для получения товара по ID: {}", foodId);

        try {
            Food foodFromDb = jdbcTemplate.queryForObject(SQL_SELECT_FOOD, this::mapRow,foodId);

            return Optional.ofNullable(foodFromDb);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Step("Удален товара с ID: {foodId} через SQL-запрос")
    @Override
    public void removeProductById(int foodId) {
        log.info("Выполнение SQL-запроса для удаления записи по ID: {}", foodId);

        int deletedRow = jdbcTemplate.update(SQL_DELETE_FOOD, foodId);

        if (deletedRow == 0) {
            Assertions.fail(String.format("Некорректный ID товара. Товар с ID: %d не был удален", foodId));
        }
    }

    @Step("Выполнена проверка наличия таблицы: {tableName} в схеме БД")
    @Override
    public void checkTable(String tableName) {
        log.info("Проверка наличия таблицы {} в схеме БД", tableName);

        List<String> tables = jdbcTemplate.query(SQL_SHOW_TABLES,
                (rs, rowNum) -> rs.getString(1));

        boolean tableExists = false;
        for (String table : tables) {
            if (table.equalsIgnoreCase(table)) {
                tableExists = true;
                break;
            }
        }

        Assertions.assertTrue(tableExists,
                String.format("Таблица: %s не найдена в БД", tableName));
    }

    @Step("Выполнена проверка наличия товара в таблице по ID: {foodId}")
    @Override
    public void checkAddedFoodInTableById(int foodId) {
        log.info("Проверка наличия товара по ID: {}", foodId);

        Optional<Food> foodOptional = findById(foodId);

        Assertions.assertTrue(foodOptional.isPresent());
    }

    @Step("Выполнена проверка товара на соответствие товару в базе данных. {expected}, {actual}")
    @Override
    public void checkFoodForCompliance(Food expected, Food actual) {
        log.info("Проверка товара на соответствие. {}, {}", expected, actual);

        Assertions.assertEquals(expected, actual);
    }

    @Step("Выполнена проверка отсутствия товара в таблице по ID: {foodId}")
    @Override
    public void checkThatFoodHasBeenRemoved(int foodId) {
        log.info("Проверка отсутствия товара по ID: {}", foodId);

        Optional<Food> foodOptional = findById(foodId);

        Assertions.assertFalse(foodOptional.isPresent());
    }

    @Override
    public Food createTestFood(String foodName, FoodType foodType, boolean exoticStatus) {
        return Food.builder()
                .name(foodName)
                .type(foodType)
                .exotic(exoticStatus)
                .build();
    }

    private Food mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Food.builder()
                .id(resultSet.getInt("food_id"))
                .name(resultSet.getString("food_name"))
                .type(FoodType.valueOf(resultSet.getString("food_type")))
                .exotic(resultSet.getBoolean("food_exotic"))
                .build();
    }
}