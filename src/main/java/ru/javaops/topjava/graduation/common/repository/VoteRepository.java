package ru.javaops.topjava.graduation.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.topjava.graduation.common.model.User;
import ru.javaops.topjava.graduation.common.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    Optional<Vote> findByUserAndDate(User user, LocalDate date);

    Optional<Vote> findByUserIdAndDate(Integer userId, LocalDate date);

    boolean existsByUserIdAndDate(Integer userId, LocalDate date);
}
