package ru.ibs.practice.tests.steps;

import io.cucumber.java.ru.*;
import ru.ibs.practice.framework.manager.db.DataBaseManager;
import ru.ibs.practice.framework.model.Food;
import ru.ibs.practice.framework.model.FoodType;
import ru.ibs.practice.tests.hooks.DbHooks;

public class DataBaseSteps {
    private final DataBaseManager manager = DbHooks.getDataBaseManager();
    private Food foodBeforeAdding;
    private Food foodAfterAdding;

    @Дано("Выполнить проверку таблицы {string} в базе данных")
    public void checkTable(String tableName) {
        manager.checkTable(tableName);
    }

    @Когда("Добавляется товар в базу данных. Наименование: {string}, тип: {string}, экзотик статус: {booleanValue}")
    public void addProductInDb(String foodName, String foodType, Boolean exoticState) {
        foodBeforeAdding = manager.createTestFood(foodName, FoodType.valueOf(foodType), exoticState);
        foodAfterAdding = manager.addProduct(foodBeforeAdding);
    }

    @Тогда("Товар успешно добавляется в базу данных")
    public void checkProductAdded() {
        manager.checkAddedFoodInTableById(foodAfterAdding.getId());
    }

    @И("Товар соответствует ожидаемым значениям")
    public void checkProductCompliance() {
        manager.checkFoodForCompliance(foodBeforeAdding, foodAfterAdding);
    }

    @Затем("Товар удаляется из базы данных")
    public void removeProductFromDb() {
        manager.removeProductById(foodAfterAdding.getId());
    }

    @И("Товар успешно удален из базы данных")
    public void checkProductDeleted() {
        manager.checkThatFoodHasBeenRemoved(foodAfterAdding.getId());
    }
}