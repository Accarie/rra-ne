package com.ne.template.owners;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PlateNumberDTO(
        @NotBlank
        @Pattern(regexp = "^[A-Z]{2,3}\\d{3}[A-Z]$", message = "Invalid plate number format")
        String plateNumber,

        @NotNull
        @PastOrPresent
        LocalDate issuedDate,

        @NotNull
        Long ownerId
) {

}
