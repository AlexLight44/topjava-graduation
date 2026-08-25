package ru.javaops.topjava.graduation.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.topjava.graduation.common.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

}
