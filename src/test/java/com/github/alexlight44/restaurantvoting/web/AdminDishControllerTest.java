package com.github.alexlight44.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.alexlight44.restaurantvoting.model.Dish;
import com.github.alexlight44.restaurantvoting.repository.DishRepository;
import com.github.alexlight44.restaurantvoting.util.JsonUtil;
import com.github.alexlight44.restaurantvoting.testutil.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.alexlight44.restaurantvoting.testutil.DishTestData.DISH1_ID;
import static com.github.alexlight44.restaurantvoting.testutil.DishTestData.DISH_MATCHER;
import static com.github.alexlight44.restaurantvoting.testutil.DishTestData.getNew;
import static com.github.alexlight44.restaurantvoting.testutil.DishTestData.getUpdated;
import static com.github.alexlight44.restaurantvoting.testutil.RestaurantTestData.RESTAURANT1_ID;
import static com.github.alexlight44.restaurantvoting.testutil.UserTestData.ADMIN_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {

    @Autowired
    private DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post("/api/admin/restaurants/" + RESTAURANT1_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.findById(newId).orElseThrow(), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put("/api/admin/restaurants/" + RESTAURANT1_ID + "/dishes/" + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(dishRepository.findById(DISH1_ID).orElseThrow(), getUpdated());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateWithoutMenuDate() throws Exception {
        Dish updated = getUpdated();
        updated.setMenuDate(null);
        perform(MockMvcRequestBuilders.put("/api/admin/restaurants/" + RESTAURANT1_ID + "/dishes/" + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete("/api/admin/restaurants/" + RESTAURANT1_ID + "/dishes/" + DISH1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(DISH1_ID).isPresent());
    }
}
