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
@RequestMapping("/api/vehicle-owners")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class VehicleOwnerController {

    private final VehicleOwnerService ownerService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<VehicleOwner> registerOwner(@Valid @RequestBody VehicleOwnerDTO request) {
        return ResponseEntity.ok(ownerService.registerOwner(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<VehicleOwner>> getAllOwners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ownerService.getAllOwners(PageRequest.of(page, size)));
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<VehicleOwner>> searchOwners(
            @RequestParam String searchTerm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ownerService.searchOwners(searchTerm, PageRequest.of(page, size)));
    }
}