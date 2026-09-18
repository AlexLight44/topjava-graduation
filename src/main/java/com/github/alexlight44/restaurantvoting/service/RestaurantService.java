package com.github.alexlight44.restaurantvoting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.alexlight44.restaurantvoting.model.Dish;
import com.github.alexlight44.restaurantvoting.model.Restaurant;
import com.github.alexlight44.restaurantvoting.repository.DishRepository;
import com.github.alexlight44.restaurantvoting.repository.RestaurantRepository;
import com.github.alexlight44.restaurantvoting.to.DishTo;
import com.github.alexlight44.restaurantvoting.to.RestaurantTo;
import com.github.alexlight44.restaurantvoting.validation.ValidationUtil;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final Clock clock;

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant get(int id) {
        return ValidationUtil.checkFound(restaurantRepository.findById(id), "Restaurant id=" + id + " not found");
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAllByOrderByName();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "restaurants", key = "T(java.time.LocalDate).now(@clock)")
    public List<RestaurantTo> getAllWithTodayMenu() {
        LocalDate today = LocalDate.now(clock);
        List<Restaurant> restaurants = restaurantRepository.findAllByOrderByName();
        Map<Integer, List<Dish>> dishesByRestaurant = dishRepository.findByMenuDateOrderByName(today).stream()
                .collect(Collectors.groupingBy(d -> d.getRestaurant().getId()));
        return restaurants.stream()
                .map(r -> new RestaurantTo(r.getId(), r.getName(),
                        dishesByRestaurant.getOrDefault(r.getId(), List.of()).stream()
                                .map(d -> new DishTo(d.getId(), d.getName(), d.getPrice()))
                                .toList()))
                .toList();
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant, int id) {
        get(id);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(int id) {
        get(id);
        restaurantRepository.deleteById(id);
    }
}
