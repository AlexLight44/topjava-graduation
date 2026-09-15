package com.github.alexlight44.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.alexlight44.restaurantvoting.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailIgnoreCase(String email);

}
