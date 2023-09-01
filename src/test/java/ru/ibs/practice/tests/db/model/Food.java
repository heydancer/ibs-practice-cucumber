package ru.ibs.practice.tests.db.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Food {
    private Integer id;
    private String name;
    private String type;
    private int exotic;
}
