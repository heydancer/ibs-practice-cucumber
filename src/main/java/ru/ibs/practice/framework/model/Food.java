package ru.ibs.practice.framework.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@Builder
@ToString
public class Food {
    private Integer id;
    private String name;
    private FoodType type;
    private boolean exotic;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return exotic == food.exotic && Objects.equals(name, food.name) && type == food.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, exotic);
    }
}