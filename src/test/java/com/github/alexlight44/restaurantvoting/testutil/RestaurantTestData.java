package com.github.alexlight44.restaurantvoting.testutil;

import com.github.alexlight44.restaurantvoting.model.Restaurant;
import com.github.alexlight44.restaurantvoting.to.RestaurantTo;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 100;

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }
}
