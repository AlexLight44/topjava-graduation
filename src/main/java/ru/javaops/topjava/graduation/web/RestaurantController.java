package ru.javaops.topjava.graduation.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava.graduation.service.RestaurantService;
import ru.javaops.topjava.graduation.to.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping(RestaurantController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "Restaurant")
public class RestaurantController {

    public static final String REST_URL = "/api/restaurants";

    private final RestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "Get restaurants with today's menus")
    public List<RestaurantTo> getAll() {
        return restaurantService.getAllWithTodayMenu();
    }
}
