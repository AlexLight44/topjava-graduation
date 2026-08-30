package ru.javaops.topjava.graduation.testutil;

import ru.javaops.topjava.graduation.common.model.Dish;
import ru.javaops.topjava.graduation.common.to.DishTo;

import java.time.LocalDate;

import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.restaurant1;
import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.restaurant2;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 4;
    public static final int DISH5_ID = 5;

    public static final LocalDate TODAY = LocalDate.now();

    public static final Dish pizza = new Dish(DISH1_ID, "Pizza Margherita", 500, TODAY, restaurant1);
    public static final Dish spaghetti = new Dish(DISH2_ID, "Spaghetti", 400, TODAY, restaurant1);
    public static final Dish caesarSalad = new Dish(DISH3_ID, "Caesar Salad", 300, TODAY, restaurant1);
    public static final Dish philadelphia = new Dish(DISH4_ID, "Philadelphia", 700, TODAY, restaurant2);
    public static final Dish misoSoup = new Dish(DISH5_ID, "Miso soup", 250, TODAY, restaurant2);

    public static final DishTo pizzaTo = new DishTo(DISH1_ID, "Pizza Margherita", 500);
    public static final DishTo spaghettiTo = new DishTo(DISH2_ID, "Spaghetti", 400);
    public static final DishTo caesarSaladTo = new DishTo(DISH3_ID, "Caesar Salad", 300);
    public static final DishTo philadelphiaTo = new DishTo(DISH4_ID, "Philadelphia", 700);
    public static final DishTo misoSoupTo = new DishTo(DISH5_ID, "Miso soup", 250);

    public static Dish getNew() {
        return new Dish(null, "New Dish", 350, TODAY, null);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Pizza Margherita Updated", 550, TODAY, restaurant1);
    }
}
