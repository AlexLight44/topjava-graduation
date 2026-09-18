package com.github.alexlight44.restaurantvoting.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.alexlight44.restaurantvoting.to.VoteTo;
import com.github.alexlight44.restaurantvoting.testutil.AbstractControllerTest;
import com.github.alexlight44.restaurantvoting.util.JsonUtil;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.alexlight44.restaurantvoting.testutil.RestaurantTestData.NOT_FOUND;
import static com.github.alexlight44.restaurantvoting.testutil.RestaurantTestData.RESTAURANT1_ID;
import static com.github.alexlight44.restaurantvoting.testutil.RestaurantTestData.RESTAURANT2_ID;
import static com.github.alexlight44.restaurantvoting.testutil.UserTestData.ADMIN_MAIL;
import static com.github.alexlight44.restaurantvoting.testutil.UserTestData.USER_MAIL;
import static com.github.alexlight44.restaurantvoting.testutil.VoteTestData.VOTE_MATCHER;
import static com.github.alexlight44.restaurantvoting.testutil.VoteTestData.getNew;
import static com.github.alexlight44.restaurantvoting.testutil.VoteTestData.vote1;
import static com.github.alexlight44.restaurantvoting.web.VoteController.REST_URL;

class VoteControllerTest extends AbstractControllerTest {

    @BeforeEach
    void setClockBeforeDeadline() {
        clock.setTime(LocalTime.of(10, 0));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote1(clock)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllEmpty() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(VOTE_MATCHER.contentJson());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        VoteTo newVote = getNew(clock);
        ResultActions action = perform(votePost(RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.restaurantId").value(RESTAURANT1_ID));

        VoteTo created = VOTE_MATCHER.readFromJson(action);
        newVote.setId(created.getId());
        VOTE_MATCHER.assertMatch(created, newVote);

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(VOTE_MATCHER.contentJson(created, vote1(clock)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteTwice() throws Exception {
        perform(votePost(RESTAURANT1_ID))
                .andExpect(status().isCreated());
        perform(votePost(RESTAURANT2_ID))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void changeVoteBeforeDeadline() throws Exception {
        ResultActions first = perform(votePost(RESTAURANT1_ID))
                .andExpect(status().isCreated());
        VoteTo created = VOTE_MATCHER.readFromJson(first);

        perform(votePut(RESTAURANT2_ID))
                .andDo(print())
                .andExpect(status().isNoContent());

        perform(MockMvcRequestBuilders.get(REST_URL + "/today"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.restaurantId").value(RESTAURANT2_ID));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void changeVoteAfterDeadline() throws Exception {
        perform(votePost(RESTAURANT1_ID))
                .andExpect(status().isCreated());

        clock.setTime(LocalTime.of(11, 1));
        perform(votePut(RESTAURANT2_ID))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void changeVoteWhenNotVoted() throws Exception {
        perform(votePut(RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteNotFound() throws Exception {
        perform(votePost(NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private static MockHttpServletRequestBuilder votePost(int restaurantId) {
        return MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new VoteTo(null, null, restaurantId)));
    }

    private static MockHttpServletRequestBuilder votePut(int restaurantId) {
        return MockMvcRequestBuilders.put(REST_URL + "/today")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new VoteTo(null, null, restaurantId)));
    }
}
