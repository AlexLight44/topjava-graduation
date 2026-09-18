package com.github.alexlight44.restaurantvoting.testutil;

import com.github.alexlight44.restaurantvoting.model.Dish;
import com.github.alexlight44.restaurantvoting.to.DishTo;

import java.time.LocalDate;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH1_ID = 1;

    public static final DishTo pizzaTo = new DishTo(1, "Pizza Margherita", 500);
    public static final DishTo spaghettiTo = new DishTo(2, "Spaghetti", 400);
    public static final DishTo caesarSaladTo = new DishTo(3, "Caesar Salad", 300);
    public static final DishTo philadelphiaTo = new DishTo(4, "Philadelphia", 700);
    public static final DishTo misoSoupTo = new DishTo(5, "Miso soup", 250);

    public static Dish getNew() {
        return new Dish(null, "New Dish", 350, LocalDate.of(2000, 1, 1), null);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Pizza Margherita Updated", 550, LocalDate.of(2000, 1, 2), null);
    }
}
