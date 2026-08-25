package ru.javaops.topjava.graduation.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.javaops.topjava.graduation.common.model.Dish;

public interface DishRepository extends JpaRepository<Dish, Integer> {

    public Dish findDishesByRestaurant();
}
