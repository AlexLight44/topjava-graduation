package ru.javaops.topjava.graduation.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava.graduation.to.RestaurantTo;
import ru.javaops.topjava.graduation.testutil.AbstractControllerTest;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava.graduation.web.RestaurantController.REST_URL;
import static ru.javaops.topjava.graduation.testutil.DishTestData.caesarSaladTo;
import static ru.javaops.topjava.graduation.testutil.DishTestData.misoSoupTo;
import static ru.javaops.topjava.graduation.testutil.DishTestData.philadelphiaTo;
import static ru.javaops.topjava.graduation.testutil.DishTestData.pizzaTo;
import static ru.javaops.topjava.graduation.testutil.DishTestData.spaghettiTo;
import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.RESTAURANT2_ID;
import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.RESTAURANT3_ID;
import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.RESTAURANT_TO_MATCHER;
import static ru.javaops.topjava.graduation.testutil.UserTestData.USER_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(
                        new RestaurantTo(RESTAURANT3_ID, "Burger House", List.of()),
                        new RestaurantTo(RESTAURANT1_ID, "Italian Place", List.of(caesarSaladTo, pizzaTo, spaghettiTo)),
                        new RestaurantTo(RESTAURANT2_ID, "Sushi City", List.of(misoSoupTo, philadelphiaTo))));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
