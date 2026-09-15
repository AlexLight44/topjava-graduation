package com.github.alexlight44.restaurantvoting.testutil;

import com.github.alexlight44.restaurantvoting.to.VoteTo;

import java.time.LocalDate;

import static com.github.alexlight44.restaurantvoting.testutil.RestaurantTestData.RESTAURANT1_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static final int VOTE1_ID = 1;

    public static VoteTo vote1() {
        return new VoteTo(VOTE1_ID, LocalDate.now().minusDays(1), RESTAURANT1_ID);
    }

    public static VoteTo getNew() {
        return new VoteTo(null, LocalDate.now(), RESTAURANT1_ID);
    }
}
