package com.ne.template.owners;

import jakarta.validation.constraints.NotBlank;




public record VehicleOwnerDTO(
        @NotBlank
        String ownerNames,
        @NotBlank
        String nationalId,
        @NotBlank
        String phoneNumber,
        @NotBlank
        String address

) {
}
