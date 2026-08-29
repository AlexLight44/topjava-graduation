package ru.javaops.topjava.graduation.common.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava.graduation.common.model.Dish;
import ru.javaops.topjava.graduation.common.model.Restaurant;
import ru.javaops.topjava.graduation.common.service.DishService;
import ru.javaops.topjava.graduation.common.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final DishService dishService;

    @GetMapping
    private List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}/dishes")
    public List<Dish> getTodayMenu(@PathVariable int id) {
        return dishService.getTodayMenu(id);
    }
}
