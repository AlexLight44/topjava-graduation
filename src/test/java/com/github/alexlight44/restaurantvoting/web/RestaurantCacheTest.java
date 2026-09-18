package com.github.alexlight44.restaurantvoting.web;

import com.github.alexlight44.restaurantvoting.service.RestaurantService;
import com.github.alexlight44.restaurantvoting.to.RestaurantTo;
import com.github.alexlight44.restaurantvoting.testutil.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@TestPropertySource(properties = {
        "spring.cache.type=caffeine",
        "spring.cache.cache-names=restaurants",
        "spring.cache.caffeine.spec=maximumSize=10,expireAfterAccess=60s"
})
class RestaurantCacheTest extends AbstractControllerTest {

    private static final ZoneId ZONE = ZoneId.systemDefault();

    @Autowired
    private RestaurantService restaurantService;

    @MockBean
    private Clock clock;

    @Test
    void getAllWithTodayMenuDoesNotReuseCacheAfterDateChange() {
        setDate(LocalDate.now());
        List<RestaurantTo> today = restaurantService.getAllWithTodayMenu();
        assertTrue(today.stream().anyMatch(r -> !r.getDishes().isEmpty()));

        setDate(LocalDate.now().plusDays(1));
        List<RestaurantTo> nextDay = restaurantService.getAllWithTodayMenu();
        assertTrue(nextDay.stream().allMatch(r -> r.getDishes().isEmpty()));
        assertFalse(today.equals(nextDay));
    }

    private void setDate(LocalDate date) {
        when(clock.instant()).thenReturn(date.atTime(LocalTime.NOON).atZone(ZONE).toInstant());
        when(clock.getZone()).thenReturn(ZONE);
    }
}
