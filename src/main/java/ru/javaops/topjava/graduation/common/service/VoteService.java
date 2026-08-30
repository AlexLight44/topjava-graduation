package ru.javaops.topjava.graduation.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.graduation.common.error.DataConflictException;
import ru.javaops.topjava.graduation.common.error.NotFoundException;
import ru.javaops.topjava.graduation.common.model.Restaurant;
import ru.javaops.topjava.graduation.common.model.User;
import ru.javaops.topjava.graduation.common.model.Vote;
import ru.javaops.topjava.graduation.common.repository.RestaurantRepository;
import ru.javaops.topjava.graduation.common.repository.VoteRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    public static final LocalTime DEADLINE = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final Clock clock;

    @Transactional
    public Vote vote(User user, int restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant id=" + restaurantId + " not found"));

        LocalDate today = LocalDate.now(clock);
        LocalTime now = LocalTime.now(clock);

        Optional<Vote> existingVote = voteRepository.findByUserAndDate(user, today);

        if (existingVote.isPresent()) {
            if (now.isAfter(DEADLINE)) {
                throw new DataConflictException("Vote cannot be changed after 11:00");
            }
            Vote vote = existingVote.get();
            vote.setRestaurant(restaurant);
            return voteRepository.save(vote);
        }
        return voteRepository.save(new Vote(null, today, user, restaurant));
    }

    public Optional<Vote> getTodayVote(User user) {
        return voteRepository.findByUserAndDate(user, LocalDate.now(clock));
    }
}
