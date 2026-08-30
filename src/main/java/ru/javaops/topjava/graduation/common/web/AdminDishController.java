package ru.javaops.topjava.graduation.common.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava.graduation.common.model.Dish;
import ru.javaops.topjava.graduation.common.service.DishService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(AdminDishController.REST_URL)
@RequiredArgsConstructor
public class AdminDishController {

    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private final DishService dishService;

    @GetMapping
    public List<Dish> getMenu(@PathVariable int restaurantId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                              Optional<LocalDate> date) {
        return date.map(d -> dishService.getMenu(restaurantId, d))
                .orElseGet(() -> dishService.getTodayMenu(restaurantId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dish create(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        return dishService.create(dish, restaurantId);
    }
}
