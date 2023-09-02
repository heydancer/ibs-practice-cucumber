package ru.ibs.practice.tests.db.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.ibs.practice.tests.db.model.Food;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Slf4j
public class SpringDataBaseService implements DataBaseService {
    private static final String SQL_SHOW_TABLES = "SHOW TABLES;";
    private static final String SQL_INSERT_FOOD = "INSERT INTO food(food_name, food_type, food_exotic) VALUES (?, ?, ?);";
    private static final String SQL_SELECT_ALL_FOOD = "SELECT * FROM food;";
    private static final String SQL_SELECT_FOOD_ID = "SELECT food_id FROM food WHERE food_id = ?;";
    private static final String SQL_DELETE_FOOD = "DELETE FROM food WHERE food_id = ?;";
    private final JdbcTemplate jdbcTemplate;
    private Food lastFood;

    public SpringDataBaseService(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addProduct(String productName, String productType, int exotic) {
        log.info("Вставка новой записи в таблицу 'FOOD'");

        int rowsInserted = jdbcTemplate.update(SQL_INSERT_FOOD, productName, productType, exotic);

        Assertions.assertEquals(1, rowsInserted, "Запись не была добавлена в таблицу");
    }

    @Override
    public void findAll() {
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

    @Override
    public void removeProduct() {
        log.info("Выполнение SQL-запроса для удаления записи из таблицы 'FOOD'");

        int rowsDeleted = jdbcTemplate.update(SQL_DELETE_FOOD, lastFood.getId());

        Assertions.assertEquals(1, rowsDeleted, "Запись не была удалена из таблицы");

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_FOOD_ID, lastFood.getId());

        Assertions.assertTrue(rows.isEmpty(),
                String.format("Запись с ID = %d не должна быть в таблице 'FOOD'", lastFood.getId()));

    }

    @Override
    public void checkTable() {
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
}