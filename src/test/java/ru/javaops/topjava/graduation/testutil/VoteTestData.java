package ru.javaops.topjava.graduation.testutil;

import ru.javaops.topjava.graduation.common.to.VoteTo;

import java.time.LocalDate;

import static ru.javaops.topjava.graduation.testutil.RestaurantTestData.RESTAURANT1_ID;

public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);

    public static VoteTo getNew() {
        return new VoteTo(null, LocalDate.now(), RESTAURANT1_ID);
    }
}
