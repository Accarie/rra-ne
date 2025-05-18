package com.ne.template.vehicles;

import com.ne.template.owners.PlateNumberDTO;
import com.ne.template.owners.VehicleOwnerDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OwnershipHistoryDTO(
        Long id,
        String vehicleChassisNumber,
        VehicleOwnerDTO owner,
        LocalDate transferDate,
        BigDecimal purchaseAmount,
        PlateNumberDTO plateNumber

) {
}
