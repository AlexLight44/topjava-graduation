package ru.javaops.topjava.graduation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.graduation.error.DataConflictException;
import ru.javaops.topjava.graduation.error.NotFoundException;
import ru.javaops.topjava.graduation.model.Restaurant;
import ru.javaops.topjava.graduation.model.User;
import ru.javaops.topjava.graduation.model.Vote;
import ru.javaops.topjava.graduation.repository.RestaurantRepository;
import ru.javaops.topjava.graduation.repository.VoteRepository;
import ru.javaops.topjava.graduation.to.VoteTo;

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
    public VoteTo vote(User user, int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant id=" + restaurantId + " not found"));

        LocalDate today = LocalDate.now(clock);
        LocalTime now = LocalTime.now(clock);

        Optional<Vote> existingVote = voteRepository.findByUserAndVoteDate(user, today);

        if (existingVote.isPresent()) {
            if (now.isAfter(DEADLINE)) {
                throw new DataConflictException("Vote cannot be changed after 11:00");
            }
            Vote vote = existingVote.get();
            vote.setRestaurant(restaurant);
            return toTo(voteRepository.save(vote));
        }
        return toTo(voteRepository.save(new Vote(null, today, user, restaurant)));
    }

    public Optional<VoteTo> getTodayVote(User user) {
        return voteRepository.findByUserAndVoteDate(user, LocalDate.now(clock)).map(VoteService::toTo);
    }

    private static VoteTo toTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getVoteDate(), vote.getRestaurant().getId());
    }
}
