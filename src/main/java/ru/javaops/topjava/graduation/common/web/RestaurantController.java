package ru.javaops.topjava.graduation.common.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava.graduation.common.service.RestaurantService;
import ru.javaops.topjava.graduation.common.to.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping(RestaurantController.REST_URL)
@RequiredArgsConstructor
public class RestaurantController {

    public static final String REST_URL = "/api/restaurants";

    private final RestaurantService restaurantService;

    @GetMapping
    public List<RestaurantTo> getAll() {
        return restaurantService.getAllWithTodayMenu();
    }
}
