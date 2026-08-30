package ru.javaops.topjava.graduation.common.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTo {
    private Integer id;
    private String name;
    private List<DishTo> dishes;
}
