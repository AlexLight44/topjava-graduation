package ru.javaops.topjava.graduation.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.javaops.topjava.graduation.common.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

}
