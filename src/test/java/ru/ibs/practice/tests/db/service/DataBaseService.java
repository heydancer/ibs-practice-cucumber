package ru.ibs.practice.tests.db.service;

import java.sql.SQLException;

public interface DataBaseService {
     void addProduct(String productName, String productType, int exotic);
     void findAll();
     void removeProduct();
     void checkTable();
}