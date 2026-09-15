package ru.javaops.topjava.graduation.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.topjava.graduation.model.User;
import ru.javaops.topjava.graduation.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findByUserAndVoteDate(User user, LocalDate voteDate);

    @EntityGraph(attributePaths = "restaurant")
    List<Vote> findAllByUserOrderByVoteDateDesc(User user);
}
