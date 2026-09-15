package com.github.alexlight44.restaurantvoting.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate voteDate;
    @NotNull
    private Integer restaurantId;
}
