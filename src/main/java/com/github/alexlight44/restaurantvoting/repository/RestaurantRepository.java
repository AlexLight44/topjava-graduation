package com.github.alexlight44.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.alexlight44.restaurantvoting.model.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findAllByOrderByName();
}
