package com.github.alexlight44.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import com.github.alexlight44.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> findByRestaurantIdAndMenuDateOrderByName(Integer restaurantId, LocalDate menuDate);

    List<Dish> findByMenuDate(LocalDate menuDate);
}
