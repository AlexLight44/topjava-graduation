package com.github.alexlight44.restaurantvoting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.github.alexlight44.restaurantvoting.model.Dish;
import com.github.alexlight44.restaurantvoting.service.DishService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(AdminDishController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "Admin dish")
public class AdminDishController {

    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private final DishService dishService;

    @GetMapping
    @Operation(summary = "Get restaurant menu for a date (today if omitted)")
    public List<Dish> getMenu(@PathVariable int restaurantId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                              Optional<LocalDate> date) {
        return date.map(d -> dishService.getMenu(restaurantId, d))
                .orElseGet(() -> dishService.getTodayMenu(restaurantId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create dish (menuDate defaults to today)")
    public Dish create(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        return dishService.create(dish, restaurantId);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update dish")
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int id) {
        dishService.update(dish, id, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete dish")
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        dishService.delete(id, restaurantId);
    }
}
