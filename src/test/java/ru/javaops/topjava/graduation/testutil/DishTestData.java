package ru.javaops.topjava.graduation.testutil;

import ru.javaops.topjava.graduation.common.model.Dish;

import java.time.LocalDate;

import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.restaurant1;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;

    public static final LocalDate TODAY = LocalDate.now();

    public static final Dish pizza = new Dish(DISH1_ID, "Pizza Margherita", 500, TODAY, restaurant1);
    public static final Dish spaghetti = new Dish(DISH2_ID, "Spaghetti", 400, TODAY, restaurant1);
    public static final Dish caesarSalad = new Dish(DISH3_ID, "Caesar Salad", 300, TODAY, restaurant1);

    public static Dish getNew() {
        return new Dish(null, "New Dish", 350, TODAY, null);
    }
}
