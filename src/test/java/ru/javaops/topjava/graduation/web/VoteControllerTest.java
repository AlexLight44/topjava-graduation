package ru.javaops.topjava.graduation.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava.graduation.to.VoteTo;
import ru.javaops.topjava.graduation.testutil.AbstractControllerTest;
import ru.javaops.topjava.graduation.util.JsonUtil;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.NOT_FOUND;
import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.RESTAURANT2_ID;
import static ru.javaops.topjava.graduation.testutil.UserTestData.ADMIN_MAIL;
import static ru.javaops.topjava.graduation.testutil.UserTestData.USER_MAIL;
import static ru.javaops.topjava.graduation.testutil.VoteTestData.VOTE_MATCHER;
import static ru.javaops.topjava.graduation.testutil.VoteTestData.getNew;
import static ru.javaops.topjava.graduation.testutil.VoteTestData.vote1;
import static ru.javaops.topjava.graduation.web.VoteController.REST_URL;

class VoteControllerTest extends AbstractControllerTest {

    private static final ZoneId ZONE = ZoneId.systemDefault();

    @MockBean
    private Clock clock;

    @BeforeEach
    void setClockBeforeDeadline() {
        setTime(LocalTime.of(10, 0));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote1()));
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
        VoteTo newVote = getNew();
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
                .andExpect(VOTE_MATCHER.contentJson(created, vote1()));
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

        setTime(LocalTime.of(11, 1));
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

    private void setTime(LocalTime time) {
        Instant instant = LocalDate.now().atTime(time).atZone(ZONE).toInstant();
        when(clock.instant()).thenReturn(instant);
        when(clock.getZone()).thenReturn(ZONE);
    }
}
