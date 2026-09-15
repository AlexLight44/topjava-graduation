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
import java.util.List;
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
    public VoteTo create(User user, int restaurantId) {
        Restaurant restaurant = getRestaurant(restaurantId);
        LocalDate today = LocalDate.now(clock);
        if (voteRepository.findByUserAndVoteDate(user, today).isPresent()) {
            throw new DataConflictException("Vote for today already exists");
        }
        return toTo(voteRepository.save(new Vote(null, today, user, restaurant)));
    }

    @Transactional
    public void updateToday(User user, int restaurantId) {
        Restaurant restaurant = getRestaurant(restaurantId);
        Vote vote = voteRepository.findByUserAndVoteDate(user, LocalDate.now(clock))
                .orElseThrow(() -> new NotFoundException("Vote for today not found"));
        if (LocalTime.now(clock).isAfter(DEADLINE)) {
            throw new DataConflictException("Vote cannot be changed after 11:00");
        }
        vote.setRestaurant(restaurant);
        voteRepository.save(vote);
    }

    private Restaurant getRestaurant(int restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant id=" + restaurantId + " not found"));
    }

    public Optional<VoteTo> getTodayVote(User user) {
        return voteRepository.findByUserAndVoteDate(user, LocalDate.now(clock)).map(VoteService::toTo);
    }

    public List<VoteTo> getAll(User user) {
        return voteRepository.findAllByUserOrderByVoteDateDesc(user).stream()
                .map(VoteService::toTo)
                .toList();
    }

    private static VoteTo toTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getVoteDate(), vote.getRestaurant().getId());
    }
}
