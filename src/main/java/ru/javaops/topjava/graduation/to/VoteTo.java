package ru.javaops.topjava.graduation.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteTo {
    private Integer id;
    private LocalDate voteDate;
    private Integer restaurantId;
}
