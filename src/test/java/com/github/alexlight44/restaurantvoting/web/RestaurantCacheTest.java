package com.github.alexlight44.restaurantvoting.web;

import com.github.alexlight44.restaurantvoting.service.RestaurantService;
import com.github.alexlight44.restaurantvoting.to.RestaurantTo;
import com.github.alexlight44.restaurantvoting.testutil.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(properties = {
        "spring.cache.type=caffeine",
        "spring.cache.cache-names=restaurants",
        "spring.cache.caffeine.spec=maximumSize=10,expireAfterAccess=60s"
})
class RestaurantCacheTest extends AbstractControllerTest {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void getAllWithTodayMenuDoesNotReuseCacheAfterDateChange() {
        clock.setDate(LocalDate.now());
        List<RestaurantTo> today = restaurantService.getAllWithTodayMenu();
        assertTrue(today.stream().anyMatch(r -> !r.getDishes().isEmpty()));

        clock.setDate(LocalDate.now().plusDays(1));
        List<RestaurantTo> nextDay = restaurantService.getAllWithTodayMenu();
        assertTrue(nextDay.stream().allMatch(r -> r.getDishes().isEmpty()));
        assertFalse(today.equals(nextDay));
    }
}
