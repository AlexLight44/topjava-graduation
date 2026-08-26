package ru.javaops.topjava.graduation.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javaops.topjava.graduation.common.error.NotFoundException;
import ru.javaops.topjava.graduation.common.model.Restaurant;
import ru.javaops.topjava.graduation.common.model.User;
import ru.javaops.topjava.graduation.common.model.Vote;
import ru.javaops.topjava.graduation.common.repository.RestaurantRepository;
import ru.javaops.topjava.graduation.common.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    public static final LocalTime DEADLINE = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    public Vote vote(User user, int restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant id=" + restaurantId + " not found"));

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Optional<Vote> existingVote = voteRepository.findByUserAndDate(user, today);

        if (existingVote.isPresent()) {
            if (now.isAfter(DEADLINE)) {
                throw new IllegalStateException("Vote cannot be changed after 11:00");
            }
            Vote vote = existingVote.get();
            vote.setRestaurant(restaurant);
            return voteRepository.save(vote);
        }
        return voteRepository.save(new Vote(null, today, user, restaurant));
    }

    public Vote getTodayVote(User user) {
        return voteRepository.findByUserAndDate(user, LocalDate.now())
                .orElseThrow(() -> new NotFoundException("Vote for today not found"));
    }
}
