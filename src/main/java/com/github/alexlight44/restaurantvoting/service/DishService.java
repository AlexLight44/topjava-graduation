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
import com.github.alexlight44.restaurantvoting.repository.RestaurantRepository;
import com.github.alexlight44.restaurantvoting.validation.ValidationUtil;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
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
        ValidationUtil.checkNew(dish);
        dish.setRestaurant(getRestaurant(restaurantId));
        if (dish.getMenuDate() == null) {
            dish.setMenuDate(LocalDate.now(clock));
        }
        return dishRepository.save(dish);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Dish dish, int id, int restaurantId) {
        ValidationUtil.assureIdConsistent(dish, id);
        Restaurant restaurant = getRestaurant(restaurantId);
        Dish db = get(id);
        if (!db.getRestaurant().getId().equals(restaurantId)) {
            throw new DataConflictException("Dish id=" + id + " doesn't belong to restaurant id=" + restaurantId);
        }
        dish.setRestaurant(restaurant);
        if (dish.getMenuDate() == null) {
            dish.setMenuDate(db.getMenuDate());
        }
        dishRepository.save(dish);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id, int restaurantId) {
        Dish db = get(id);
        if (!db.getRestaurant().getId().equals(restaurantId)) {
            throw new DataConflictException("Dish id=" + id + " doesn't belong to restaurant id=" + restaurantId);
        }
        dishRepository.deleteById(id);
    }

    private Dish get(int id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dish id=" + id + " not found"));
    }

    private Restaurant getRestaurant(int restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant id=" + restaurantId + " not found"));
    }
}
