package com.github.alexlight44.restaurantvoting.web;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.alexlight44.restaurantvoting.model.Restaurant;
import com.github.alexlight44.restaurantvoting.repository.DishRepository;
import com.github.alexlight44.restaurantvoting.repository.RestaurantRepository;
import com.github.alexlight44.restaurantvoting.repository.VoteRepository;
import com.github.alexlight44.restaurantvoting.util.JsonUtil;
import com.github.alexlight44.restaurantvoting.testutil.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.alexlight44.restaurantvoting.testutil.DishTestData.DISH1_ID;
import static com.github.alexlight44.restaurantvoting.testutil.RestaurantTestData.RESTAURANT1_ID;
import static com.github.alexlight44.restaurantvoting.testutil.RestaurantTestData.RESTAURANT_MATCHER;
import static com.github.alexlight44.restaurantvoting.testutil.RestaurantTestData.getNew;
import static com.github.alexlight44.restaurantvoting.testutil.UserTestData.ADMIN_MAIL;
import static com.github.alexlight44.restaurantvoting.testutil.UserTestData.USER_MAIL;
import static com.github.alexlight44.restaurantvoting.testutil.VoteTestData.VOTE1_ID;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private EntityManager em;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(AdminRestaurantController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.findById(newId).orElseThrow(), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Restaurant duplicate = new Restaurant(null, "Italian Place");
        perform(MockMvcRequestBuilders.post(AdminRestaurantController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(AdminRestaurantController.REST_URL + "/" + RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        em.flush();
        em.clear();
        assertFalse(restaurantRepository.findById(RESTAURANT1_ID).isPresent());
        assertFalse(dishRepository.findById(DISH1_ID).isPresent());
        assertFalse(voteRepository.findById(VOTE1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(AdminRestaurantController.REST_URL))
                .andExpect(status().isForbidden());
    }
}
