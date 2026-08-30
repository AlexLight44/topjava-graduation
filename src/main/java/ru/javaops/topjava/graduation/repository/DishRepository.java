package ru.javaops.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.topjava.graduation.model.Dish;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> findByRestaurantIdAndMenuDateOrderByName(Integer restaurantId, LocalDate menuDate);

    List<Dish> findByMenuDateOrderByName(LocalDate menuDate);
}
