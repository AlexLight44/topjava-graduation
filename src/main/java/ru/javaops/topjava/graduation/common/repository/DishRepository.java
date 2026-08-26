package ru.javaops.topjava.graduation.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.topjava.graduation.common.model.Dish;
import ru.javaops.topjava.graduation.common.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> findByRestaurantIdAndDate(Integer restaurantId, LocalDate date);
}
