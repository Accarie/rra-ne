package com.ne.template.vehicles;

import com.ne.template.owners.PlateNumber;
import com.ne.template.owners.PlateNumberService;
import com.ne.template.owners.VehicleOwner;
import com.ne.template.owners.VehicleOwnerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VehicleOwnerService vehicleOwnerService;
    private final PlateNumberService plateNumberService;

//    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<Vehicle> registerVehicle(
//            @Valid @RequestBody Vehicle vehicle,
//            @RequestParam Long ownerId,
//            @RequestParam Long plateNumberId) {
//        return ResponseEntity.ok(vehicleService.registerVehicle(vehicle, ownerId, plateNumberId));
//    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Vehicle> registerVehicle(
            @RequestBody VehicleDTO request,
            @RequestParam Long ownerId,
            @RequestParam Long plateNumberId) {

          VehicleOwner owner = vehicleOwnerService.getOwnerById(ownerId);
          PlateNumber plateNumber = plateNumberService.getPlateNumberById(plateNumberId);
          Vehicle vehicle = vehicleService.registerVehicle(request, owner, plateNumber);
          return ResponseEntity.ok(vehicle);
    }

    @PostMapping("/{vehicleId}/transfer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Vehicle> transferVehicle(
            @PathVariable Long vehicleId,
            @RequestParam Long newOwnerId,
            @RequestParam BigDecimal purchaseAmount) {
        return ResponseEntity.ok(vehicleService.transferVehicle(vehicleId, newOwnerId, purchaseAmount));
    }

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<Vehicle>> getVehiclesByOwner(
            @PathVariable Long ownerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(vehicleService.getVehiclesByOwner(ownerId, PageRequest.of(page, size)));
    }

    @GetMapping("/history")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<OwnershipHistory>> getVehicleHistory(
            @RequestParam String identifier) {
        return ResponseEntity.ok(vehicleService.getVehicleHistory(identifier));
    }
}