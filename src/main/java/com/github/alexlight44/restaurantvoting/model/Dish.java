package com.github.alexlight44.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"restaurant_id", "menu_date", "name"}, name = "uk_dish_restaurant_menu_date_name")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {

    @Schema(description = "Price in kopecks", example = "500")
    @Column(name = "price", nullable = false)
    @Min(1)
    private int price;

    @Schema(accessMode = Schema.AccessMode.READ_WRITE, example = "2026-08-31")
    @NotNull
    @Column(name = "menu_date", nullable = false)
    private LocalDate menuDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish(Integer id, String name, int price, LocalDate menuDate, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.menuDate = menuDate;
        this.restaurant = restaurant;
    }
}
