package com.github.alexlight44.restaurantvoting.testutil;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class MutableClock extends Clock {

    private final ZoneId zone;
    private Instant instant;

    public MutableClock() {
        this(ZoneId.systemDefault());
    }

    public MutableClock(ZoneId zone) {
        this.zone = zone;
        this.instant = Clock.system(zone).instant();
    }

    public void set(LocalDate date, LocalTime time) {
        instant = date.atTime(time).atZone(zone).toInstant();
    }

    public void setTime(LocalTime time) {
        set(LocalDate.ofInstant(instant, zone), time);
    }

    public void setDate(LocalDate date) {
        set(date, LocalTime.ofInstant(instant, zone));
    }

    public void reset() {
        instant = Clock.system(zone).instant();
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        MutableClock clock = new MutableClock(zone);
        clock.instant = instant;
        return clock;
    }

    @Override
    public Instant instant() {
        return instant;
    }
}
