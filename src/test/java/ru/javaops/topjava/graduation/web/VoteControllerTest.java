package ru.javaops.topjava.graduation.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava.graduation.common.to.VoteTo;
import ru.javaops.topjava.graduation.testutil.AbstractControllerTest;

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
import static ru.javaops.topjava.graduation.testutil.UserTestData.USER_MAIL;
import static ru.javaops.topjava.graduation.testutil.VoteTestData.VOTE_MATCHER;
import static ru.javaops.topjava.graduation.testutil.VoteTestData.getNew;

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
    void vote() throws Exception {
        VoteTo newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(voteUrl(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.restaurantId").value(RESTAURANT1_ID));

        VoteTo created = VOTE_MATCHER.readFromJson(action);
        newVote.setId(created.getId());
        VOTE_MATCHER.assertMatch(created, newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void changeVoteBeforeDeadline() throws Exception {
        ResultActions first = perform(MockMvcRequestBuilders.post(voteUrl(RESTAURANT1_ID)))
                .andExpect(status().isOk());
        VoteTo created = VOTE_MATCHER.readFromJson(first);

        perform(MockMvcRequestBuilders.post(voteUrl(RESTAURANT2_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.restaurantId").value(RESTAURANT2_ID));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void changeVoteAfterDeadline() throws Exception {
        perform(MockMvcRequestBuilders.post(voteUrl(RESTAURANT1_ID)))
                .andExpect(status().isOk());

        setTime(LocalTime.of(11, 1));
        perform(MockMvcRequestBuilders.post(voteUrl(RESTAURANT2_ID)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.post(voteUrl(NOT_FOUND)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private static String voteUrl(int restaurantId) {
        return "/api/restaurants/" + restaurantId + "/votes";
    }

    private void setTime(LocalTime time) {
        Instant instant = LocalDate.now().atTime(time).atZone(ZONE).toInstant();
        when(clock.instant()).thenReturn(instant);
        when(clock.getZone()).thenReturn(ZONE);
    }
}
