package ru.ibs.practice.framework.manager.db.impl;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import ru.ibs.practice.framework.manager.db.DataBaseManager;
import ru.ibs.practice.framework.model.Food;
import ru.ibs.practice.framework.model.FoodType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class JDBCManager implements DataBaseManager {
    private static final String SQL_SHOW_TABLES = "SHOW TABLES;";
    private static final String SQL_INSERT_FOOD = "INSERT INTO food(food_name, food_type, food_exotic) VALUES (?, ?, ?);";
    private static final String SQL_SELECT_ALL_FOOD = "SELECT * FROM food;";
    private static final String SQL_SELECT_FOOD = "SELECT * FROM food WHERE food_id = ?;";
    private static final String SQL_DELETE_FOOD = "DELETE FROM food WHERE food_id = ?;";
    private final Connection connection;

    public JDBCManager(Connection connection) {
        this.connection = connection;
    }

    @Step("Добавлена новая запись в таблицу {productName}, {type}, {exoticStatus}")
    @Override
    public Food addProduct(Food food) {
        log.info("Вставка новой записи в таблицу productName: {}, type: {}, exoticStatus: {}",
                food.getName(), food.getType(), food.isExotic());

        int foodId = 0;

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SQL_INSERT_FOOD, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, food.getName());
            preparedStatement.setString(2, food.getType().toString());
            preparedStatement.setBoolean(3, food.isExotic());

            int updatedRow = preparedStatement.executeUpdate();

            if (updatedRow == 0) {
                Assertions.fail(String.format("Товар не был добавлен в таблицу. Проверьте корректность товара: %s", food));
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                foodId = generatedKeys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        List<Food> foodList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_FOOD)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                foodList.add(Food.builder()
                        .id(rs.getInt("food_id"))
                        .name(rs.getString("food_name"))
                        .type(FoodType.valueOf(rs.getString("food_type")))
                        .exotic(rs.getBoolean("food_exotic"))
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return foodList;
    }

    @Step("Выполнен SQL-запроса для получения товара по ID {foodId}")
    @Override
    public Optional<Food> findById(int foodId) {
        log.info("Выполнение SQL-запроса для получения товара по ID: {}", foodId);

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_FOOD)) {
            preparedStatement.setInt(1, foodId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Food foodFromDb = Food.builder()
                        .id(rs.getInt("food_id"))
                        .name(rs.getString("food_name"))
                        .type(FoodType.valueOf(rs.getString("food_type")))
                        .exotic(rs.getBoolean("food_exotic"))
                        .build();

                return Optional.of(foodFromDb);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Step("Удален товара с ID: {foodId} через SQL-запрос")
    @Override
    public void removeProductById(int foodId) {
        log.info("Выполнение SQL-запроса для удаления записи по ID: {}", foodId);

        try (PreparedStatement deleteStatement = connection.prepareStatement(SQL_DELETE_FOOD)) {
            deleteStatement.setInt(1, foodId);

            int deletedRow = deleteStatement.executeUpdate();

            if (deletedRow == 0) {
                Assertions.fail(String.format("Некорректный ID товара. Товар с ID: %d не был удален", foodId));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Step("Выполнена проверка наличия таблицы: {tableName} в схеме БД")
    @Override
    public void checkTable(String tableName) {
        log.info("Проверка наличия таблицы {} в схеме БД", tableName);

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SHOW_TABLES);
             ResultSet rs = preparedStatement.executeQuery()) {

            boolean tableExists = false;

            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase(tableName)) {
                    tableExists = true;
                    break;
                }
            }

            Assertions.assertTrue(tableExists,
                    String.format("Таблица: %s не найдена в БД", tableName));

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}