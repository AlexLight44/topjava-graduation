package ru.javaops.topjava.graduation.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javaops.topjava.graduation.common.error.NotFoundException;
import ru.javaops.topjava.graduation.common.model.Restaurant;
import ru.javaops.topjava.graduation.common.repository.RestaurantRepository;
import ru.javaops.topjava.graduation.common.validation.ValidationUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant create(Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant get(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant id=" + id + " not found"));
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public void update(Restaurant restaurant, int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }
}
