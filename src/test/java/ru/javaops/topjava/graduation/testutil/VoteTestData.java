package ru.javaops.topjava.graduation.testutil;

import ru.javaops.topjava.graduation.common.model.Vote;

import java.time.LocalDate;

import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.restaurant1;
import static ru.javaops.topjava.graduation.testutil.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), user, restaurant1);
    }
}
