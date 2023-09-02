package ru.ibs.practice.tests.db.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import ru.ibs.practice.tests.db.model.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcDataBaseService implements DataBaseService {
    private static final String SQL_SHOW_TABLES = "SHOW TABLES;";
    private static final String SQL_INSERT_FOOD = "INSERT INTO food(food_name, food_type, food_exotic) VALUES (?, ?, ?);";
    private static final String SQL_SELECT_ALL_FOOD = "SELECT * FROM food;";
    private static final String SQL_SELECT_FOOD_ID = "SELECT food_id FROM food WHERE food_id = ?;";
    private static final String SQL_DELETE_FOOD = "DELETE FROM food WHERE food_id = ?;";
    private Food lastFood;
    private final Connection connection;

    public JdbcDataBaseService(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void addProduct(String productName, String productType, int exotic) {
        log.info("Вставка новой записи в таблицу 'FOOD'");

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_FOOD)) {
            preparedStatement.setString(1, productName);
            preparedStatement.setString(2, productType);
            preparedStatement.setInt(3, exotic);

            int rowsInserted = preparedStatement.executeUpdate();

            Assertions.assertEquals(1, rowsInserted, "Запись не была добавлена в таблицу");
        } catch (SQLException e) {
            Assertions.fail("Ошибка добавления товара", e);
        }
    }

    @Override
    public void findAll() {
        log.info("Выполнение SQL-запроса для получения списка всех товаров таблицы 'FOOD'");

        List<Food> foodList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_FOOD)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                foodList.add(Food.builder()
                        .id(rs.getInt("food_id"))
                        .name(rs.getString("food_name"))
                        .type(rs.getString("food_type"))
                        .exotic(rs.getInt("food_exotic"))
                        .build());
            }

            lastFood = foodList.get(foodList.size() - 1);

            Assertions.assertFalse(foodList.isEmpty(),
                    "Записи не найдены");
            Assertions.assertNotNull(lastFood.getId(),
                    "ID не должен быть null");
            Assertions.assertEquals("Морковь", lastFood.getName(),
                    "Значение столбца 'food_name' не соответствует значению 'Морковь'");
            Assertions.assertEquals("VEGETABLE", lastFood.getType(),
                    "Значение столбца 'food_type' не соответствует значению 'VEGETABLE'");
            Assertions.assertEquals(0, lastFood.getExotic(),
                    "Значение столбца 'food_exotic не соответствует значению '0' -> не экзотический");
        } catch (SQLException e) {
            Assertions.fail("Ошибка получения списка товаров", e);
        }
    }

    @Override
    public void removeProduct() {
        log.info("Выполнение SQL-запроса для удаления записи из таблицы 'FOOD'");

        try (PreparedStatement deleteStatement = connection.prepareStatement(SQL_DELETE_FOOD)) {
            deleteStatement.setInt(1, lastFood.getId());

            int rowsDeleted = deleteStatement.executeUpdate();

            Assertions.assertEquals(1, rowsDeleted, "Запись не была удалена из таблицы");
        } catch (SQLException e) {
            Assertions.fail("Ошибка удаления товара", e);
        }

        try (PreparedStatement selectStatement = connection.prepareStatement(SQL_SELECT_FOOD_ID)) {
            selectStatement.setInt(1, lastFood.getId());
            ResultSet rs = selectStatement.executeQuery();

            Assertions.assertFalse(rs.next(), String.format("Запись с ID = %d не должна быть в таблице 'FOOD'", lastFood.getId()));
        } catch (SQLException e) {
            Assertions.fail("Ошибка при проверке удаления товара", e);
        }
    }

    @Override
    public void checkTable() {
        log.info("Проверка наличия таблицы 'FOOD' в схеме БД");

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SHOW_TABLES);
             ResultSet rs = preparedStatement.executeQuery()) {

            boolean tableExists = false;

            while (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase("food")) {
                    tableExists = true;
                    break;
                }
            }

            Assertions.assertTrue(tableExists, "Таблица 'FOOD' не найдена в БД");

        } catch (SQLException e) {
            Assertions.fail("Ошибка проверки таблицы", e);
        }
    }
}