package ru.javaops.topjava.graduation.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.topjava.graduation.common.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailContainingIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String name);

    Optional<User> findByEmailIgnoreCase(String email);

}
