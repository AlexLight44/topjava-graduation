package ru.javaops.topjava.graduation.to;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    private LocalDate voteDate;
    private Integer restaurantId;
}
