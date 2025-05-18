package com.ne.template.vehicles;

import com.ne.template.owners.PlateNumberDTO;
import com.ne.template.owners.VehicleOwnerDTO;

import java.math.BigDecimal;

public record VehicleDTO(
        String chassisNumber,
        String manufacturer,
        Integer manufactureYear,
        BigDecimal price,
        String modelName,
        Long ownerId,
        Long plateNumberId

) {
}
