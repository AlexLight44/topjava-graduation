package ru.javaops.topjava.graduation.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javaops.topjava.graduation.common.model.Restaurant;
import ru.javaops.topjava.graduation.common.model.User;
import ru.javaops.topjava.graduation.common.model.Vote;
import ru.javaops.topjava.graduation.common.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    public Vote vote(User user, Restaurant restaurant) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        Optional<Vote> existingVote = voteRepository.findByUserAndDate(user, today);

        if (existingVote.isPresent()) {
            if (now.isAfter(LocalTime.of(11, 0))) {
                throw new IllegalStateException("Vote non change after 11:00");
            }
            Vote vote = existingVote.get();
            vote.setRestaurant(restaurant);
            return voteRepository.save(vote);
        }
        Vote newVote = new Vote(null, today, user, restaurant);
        return voteRepository.save(newVote);
    }
}
