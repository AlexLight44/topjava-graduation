package ru.javaops.topjava.graduation.common.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javaops.topjava.graduation.common.model.User;
import ru.javaops.topjava.graduation.common.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user = :user AND v.date = :date")
    Optional<Vote> findByUserAndDate(@Param("user") User user, @Param("date") LocalDate date);
}
