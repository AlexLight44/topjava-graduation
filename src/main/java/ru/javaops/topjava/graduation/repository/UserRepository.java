package ru.javaops.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.topjava.graduation.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailIgnoreCase(String email);

}
