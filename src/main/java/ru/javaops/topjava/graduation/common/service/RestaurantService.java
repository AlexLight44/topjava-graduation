package ru.javaops.topjava.graduation.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.graduation.common.error.NotFoundException;
import ru.javaops.topjava.graduation.common.model.Restaurant;
import ru.javaops.topjava.graduation.common.repository.RestaurantRepository;
import ru.javaops.topjava.graduation.common.validation.ValidationUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
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

    @Transactional
    public void update(Restaurant restaurant, int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }

    @Transactional
    public void delete(int id) {
        restaurantRepository.deleteById(id);
    }
}
