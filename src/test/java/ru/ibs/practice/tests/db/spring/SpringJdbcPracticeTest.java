package ru.ibs.practice.tests.db.spring;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ibs.practice.tests.db.model.Food;
import ru.ibs.practice.tests.db.spring.general.BaseTestDB;

import java.util.List;
import java.util.Map;

@Slf4j
@Owner("Ustiantcev Aleksandr")
@DisplayName("Проверка добавления товара в БД с использованием Spring JDBC Template")
public class SpringJdbcPracticeTest extends BaseTestDB {
    private static final String SQL_SHOW_TABLES = "SHOW TABLES;";
    private static final String SQL_INSERT_FOOD = "INSERT INTO food(food_name, food_type, food_exotic) VALUES (?, ?, ?);";
    private static final String SQL_SELECT_ALL_FOOD = "SELECT * FROM food;";
    private static final String SQL_SELECT_FOOD_ID = "SELECT food_id FROM food WHERE food_id = ?;";
    private static final String SQL_DELETE_FOOD = "DELETE FROM food WHERE food_id = ?;";
    private Food lastFood;

    @Test
    @DisplayName("Добавление товара в базу данных")
    @Description("Тестирование функциональности добавления нового товара в базу данных")
    public void testAddingProductInDb() {
        preCondition();
        addProductToDb();
        findAllProductsInDb();
        postCondition();
    }

    @Step("Проверить наличие таблицы 'FOOD' в схеме БД выполнив SQL-запрос: SHOW TABLES;")
    private void preCondition() {
        log.info("Проверка наличия таблицы 'FOOD' в схеме БД");

        List<String> tables = jdbcTemplate.query(SQL_SHOW_TABLES,
                (rs, rowNum) -> rs.getString(1));

        boolean tableExists = false;
        for (String table : tables) {
            if (table.equalsIgnoreCase("food")) {
                tableExists = true;
                break;
            }
        }

        Assertions.assertTrue(tableExists, "Таблица 'FOOD' не найдена в БД");
    }

    @Step("Выполнить SQL-запрос на добавление нового товара в таблицу FOOD")
    private void addProductToDb() {
        log.info("Вставка новой записи в таблицу 'FOOD'");

        int rowsInserted = jdbcTemplate.update(SQL_INSERT_FOOD, "Морковь", "VEGETABLE", 0);

        Assertions.assertEquals(1, rowsInserted, "Запись не была добавлена в таблицу");
    }

    @Step("Выполнить SQL-запрос для получения списка всех товаров таблицы FOOD")
    private void findAllProductsInDb() {
        log.info("Выполнение SQL-запроса для получения списка всех товаров таблицы 'FOOD'");

        List<Food> foodList = jdbcTemplate.query(SQL_SELECT_ALL_FOOD,
                (rs, rowNum) -> Food.builder()
                        .id(rs.getInt("food_id"))
                        .name(rs.getString("food_name"))
                        .type(rs.getString("food_type"))
                        .exotic(rs.getInt("food_exotic"))
                        .build());

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
    }

    @Step("Выполнение SQL-запроса для удаления записи из таблицы 'FOOD'")
    private void postCondition() {
        log.info("Выполнение SQL-запроса для удаления записи из таблицы 'FOOD'");

        int rowsDeleted = jdbcTemplate.update(SQL_DELETE_FOOD, lastFood.getId());

        Assertions.assertEquals(1, rowsDeleted, "Запись не была удалена из таблицы");

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_FOOD_ID, lastFood.getId());
        Assertions.assertTrue(rows.isEmpty(),
                String.format("Запись с ID = %d не должна быть в таблице 'FOOD'", lastFood.getId()));
    }
}