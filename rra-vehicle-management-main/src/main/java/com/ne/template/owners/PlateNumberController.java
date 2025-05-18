package com.ne.template.owners;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plate-numbers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PlateNumberController {

    private final PlateNumberService plateNumberService;
    private final VehicleOwnerService vehicleOwnerService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PlateNumber> registerPlateNumber(
            @Valid @RequestBody PlateNumberDTO request,
            @RequestParam Long ownerId) {

        VehicleOwner owner = vehicleOwnerService.getOwnerById(ownerId);
        PlateNumber plateNumber = plateNumberService.registerPlateNumber(owner, request);

        return ResponseEntity.ok(plateNumber);
    }

    @GetMapping("/owner/{ownerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<PlateNumber>> getOwnerPlateNumbers(
            @PathVariable Long ownerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(plateNumberService.getPlateNumbersByOwner(ownerId, PageRequest.of(page, size)));
    }
}