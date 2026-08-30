package ru.javaops.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "dish", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"restaurant_id", "menu_date", "name"}, name = "uk_dish_restaurant_menu_date_name")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {

    /** Price in minor currency units (kopecks). */
    @Column(name = "price", nullable = false)
    @Min(1)
    private int price;

    @Column(name = "menu_date", nullable = false)
    private LocalDate menuDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Dish(Integer id, String name, int price, LocalDate menuDate, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.menuDate = menuDate;
        this.restaurant = restaurant;
    }
}
