package ru.javaops.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.topjava.graduation.model.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> findAllByOrderByName();
}
