package com.github.alexlight44.restaurantvoting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.alexlight44.restaurantvoting.error.DataConflictException;
import com.github.alexlight44.restaurantvoting.error.NotFoundException;
import com.github.alexlight44.restaurantvoting.model.Dish;
import com.github.alexlight44.restaurantvoting.model.Restaurant;
import com.github.alexlight44.restaurantvoting.repository.DishRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantService restaurantService;
    private final Clock clock;

    public List<Dish> getMenu(int restaurantId, LocalDate menuDate) {
        return dishRepository.findByRestaurantIdAndMenuDateOrderByName(restaurantId, menuDate);
    }

    public List<Dish> getTodayMenu(int restaurantId) {
        return getMenu(restaurantId, LocalDate.now(clock));
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        dish.setRestaurant(restaurantService.get(restaurantId));
        return dishRepository.save(dish);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Dish dish, int id, int restaurantId) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        Dish oldDish = get(id);
        if (!oldDish.getRestaurant().getId().equals(restaurantId)) {
            throw new DataConflictException("Dish id=" + id + " doesn't belong to restaurant id=" + restaurantId);
        }
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id, int restaurantId) {
        Dish oldDish = get(id);
        if (!oldDish.getRestaurant().getId().equals(restaurantId)) {
            throw new DataConflictException("Dish id=" + id + " doesn't belong to restaurant id=" + restaurantId);
        }
        dishRepository.deleteById(id);
    }

    private Dish get(int id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dish id=" + id + " not found"));
    }
}
