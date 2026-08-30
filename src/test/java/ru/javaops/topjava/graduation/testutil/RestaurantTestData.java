package ru.javaops.topjava.graduation.testutil;

import ru.javaops.topjava.graduation.common.model.Restaurant;
import ru.javaops.topjava.graduation.common.to.RestaurantTo;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Italian Place");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Sushi City");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Burger House");

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }
}
