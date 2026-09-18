package com.github.alexlight44.restaurantvoting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.github.alexlight44.restaurantvoting.model.Restaurant;
import com.github.alexlight44.restaurantvoting.service.RestaurantService;
import com.github.alexlight44.restaurantvoting.validation.ValidationUtil;

import java.util.List;

@RestController
@RequestMapping(AdminRestaurantController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "Admin restaurant")
public class AdminRestaurantController {

    public static final String REST_URL = "/api/admin/restaurants";

    private final RestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "Get all restaurants")
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by id")
    public Restaurant get(@PathVariable int id) {
        return restaurantService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create restaurant")
    public Restaurant create(@Valid @RequestBody Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        return restaurantService.create(restaurant);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update restaurant")
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant")
    public void delete(@PathVariable int id) {
        restaurantService.delete(id);
    }
}
