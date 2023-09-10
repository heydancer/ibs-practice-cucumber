package ru.ibs.practice.framework.manager.ui;


import ru.ibs.practice.framework.page.FoodPage;
import ru.ibs.practice.framework.page.HomePage;

public class PageManager {
    private HomePage homePage;
    private FoodPage foodPage;
    private static PageManager INSTANCE;

    private PageManager() {
    }

    public HomePage getHomePage() {
        if (homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

    public FoodPage getFoodPage() {
        if (foodPage == null) {
            foodPage = new FoodPage();
        }
        return foodPage;
    }

    public static PageManager getPageManager() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }
}