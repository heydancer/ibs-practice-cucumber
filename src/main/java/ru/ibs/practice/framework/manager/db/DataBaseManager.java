package ru.ibs.practice.framework.manager.db;

import ru.ibs.practice.framework.model.Food;
import ru.ibs.practice.framework.model.FoodType;

import java.util.List;
import java.util.Optional;

public interface DataBaseManager {
    Food addProduct(Food food);

    List<Food> findAll();

    Optional<Food> findById(int foodId);

    void removeProductById(int foodId);

    void checkTable(String tableName);

    void checkAddedFoodInTableById(int foodId);

    void checkFoodForCompliance(Food expected, Food actual);

    void checkThatFoodHasBeenRemoved(int foodId);

    Food createTestFood(String foodName, FoodType foodType, boolean exoticStatus);
}