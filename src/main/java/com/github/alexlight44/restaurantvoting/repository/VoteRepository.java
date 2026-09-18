package com.github.alexlight44.restaurantvoting.repository;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.github.alexlight44.restaurantvoting.model.User;
import com.github.alexlight44.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findByUserAndVoteDate(User user, LocalDate voteDate);

    @EntityGraph(attributePaths = "restaurant")
    List<Vote> findAllByUserOrderByVoteDateDesc(User user);
}
