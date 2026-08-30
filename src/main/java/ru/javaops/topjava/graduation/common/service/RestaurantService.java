package ru.javaops.topjava.graduation.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.graduation.common.error.NotFoundException;
import ru.javaops.topjava.graduation.common.model.Dish;
import ru.javaops.topjava.graduation.common.model.Restaurant;
import ru.javaops.topjava.graduation.common.repository.DishRepository;
import ru.javaops.topjava.graduation.common.repository.RestaurantRepository;
import ru.javaops.topjava.graduation.common.to.DishTo;
import ru.javaops.topjava.graduation.common.to.RestaurantTo;
import ru.javaops.topjava.graduation.common.validation.ValidationUtil;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final Clock clock;

    @Transactional
    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant get(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant id=" + id + " not found"));
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAllByOrderByName();
    }

    @Cacheable("restaurants")
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
        ValidationUtil.assureIdConsistent(restaurant, id);
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
