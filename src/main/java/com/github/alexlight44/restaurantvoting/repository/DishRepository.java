package com.github.alexlight44.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.alexlight44.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> findByRestaurantIdAndMenuDateOrderByName(Integer restaurantId, LocalDate menuDate);

    List<Dish> findByMenuDateOrderByName(LocalDate menuDate);
}
