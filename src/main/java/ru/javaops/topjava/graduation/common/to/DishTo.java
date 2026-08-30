package ru.javaops.topjava.graduation.common.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishTo {
    private Integer id;
    private String name;
    private int price;
}
