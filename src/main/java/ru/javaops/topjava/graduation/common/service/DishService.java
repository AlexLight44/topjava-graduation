package ru.javaops.topjava.graduation.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.graduation.common.error.NotFoundException;
import ru.javaops.topjava.graduation.common.model.Dish;
import ru.javaops.topjava.graduation.common.model.Restaurant;
import ru.javaops.topjava.graduation.common.repository.DishRepository;
import ru.javaops.topjava.graduation.common.repository.RestaurantRepository;
import ru.javaops.topjava.graduation.common.validation.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DishService {

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;

    public List<Dish> getMenu(int restaurantId, LocalDate date) {
        return dishRepository.findByRestaurantIdAndDate(restaurantId, date);
    }

    public List<Dish> getTodayMenu(int restaurantId) {
        return getMenu(restaurantId, LocalDate.now());
    }

    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        ValidationUtil.checkNew(dish);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant id=" + restaurantId + " not found"));
        dish.setRestaurant(restaurant);

        if (dish.getDate() == null) {
            dish.setDate(LocalDate.now());
        }
        return dishRepository.save(dish);
    }

    public Dish get(int id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dish id=" + id + " no found"));
    }

    @Transactional
    public void update(Dish dish, int id, int restaurantId) {
        ValidationUtil.assureIdConsistent(dish, id);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Restaurant id=" + restaurantId + "not found"));

        dish.setRestaurant(restaurant);

        if (dish.getDate() == null) {
            dish.setDate(LocalDate.now());
        }

        dishRepository.save(dish);
    }

    @Transactional
    public void delete(int id) {
        dishRepository.deleteById(id);
    }
}
