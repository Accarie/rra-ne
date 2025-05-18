package com.ne.template.vehicles;

import com.ne.template.commons.exceptions.ResourceNotFoundException;
import com.ne.template.owners.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleOwnerService ownerService;
    private final PlateNumberService plateNumberService;
    private final OwnershipHistoryRepository historyRepository;
    private final PlateNumberRepository plateNumberRepository;

    public VehicleService(VehicleRepository vehicleRepository, VehicleOwnerService ownerService, PlateNumberService plateNumberService, OwnershipHistoryRepository historyRepository, PlateNumberRepository plateNumberRepository) {
        this.vehicleRepository = vehicleRepository;
        this.ownerService = ownerService;
        this.plateNumberService = plateNumberService;
        this.historyRepository = historyRepository;
        this.plateNumberRepository = plateNumberRepository;
    }

    public Vehicle registerVehicle(VehicleDTO dto, VehicleOwner owner, PlateNumber plateNumber) {
        log.info("Registering vehicle with chassis: {}", dto.chassisNumber());

        if (vehicleRepository.existsByChassisNumber(dto.chassisNumber())) {
            throw new IllegalArgumentException("Vehicle with this chassis number already exists");
        }

        if (!plateNumber.getOwner().equals(owner)) {
            throw new IllegalArgumentException("Plate number doesn't belong to this owner");
        }

        if (plateNumber.getStatus() != PlateStatus.AVAILABLE) {
            throw new IllegalStateException("Plate number is already in use");
        }

        plateNumber.setStatus(PlateStatus.IN_USE);

        Vehicle vehicle = new Vehicle();
        vehicle.setChassisNumber(dto.chassisNumber());
        vehicle.setManufacturer(dto.manufacturer());
        vehicle.setManufactureYear(dto.manufactureYear());
        vehicle.setPrice(dto.price());
        vehicle.setModelName(dto.modelName());
        vehicle.setCurrentOwner(owner);
        vehicle.setPlateNumber(plateNumber);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // Record initial ownership
        OwnershipHistory history = new OwnershipHistory();
        history.setVehicle(savedVehicle);
        history.setOwner(owner);
        history.setTransferDate(LocalDate.now());
        history.setPurchaseAmount(vehicle.getPrice());
        history.setPlateNumber(plateNumber);
        historyRepository.save(history);

        return savedVehicle;
    }

    public Vehicle transferVehicle(Long vehicleId, Long newOwnerId, BigDecimal purchaseAmount) {
        log.info("Transferring vehicle {} to new owner {}", vehicleId, newOwnerId);

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        VehicleOwner newOwner = ownerService.getOwnerById(newOwnerId);

        // Release current plate number
        PlateNumber currentPlate = vehicle.getPlateNumber();
        currentPlate.setStatus(PlateStatus.AVAILABLE);

        // Assign new plate number from new owner
        PlateNumber newPlate = plateNumberService.getAvailablePlateNumber(newOwnerId);
        newPlate.setStatus(PlateStatus.IN_USE);

        // Update vehicle ownership
        vehicle.setCurrentOwner(newOwner);
        vehicle.setPlateNumber(newPlate);

        // Record ownership transfer
        OwnershipHistory history = new OwnershipHistory();
        history.setVehicle(vehicle);
        history.setOwner(newOwner);
        history.setTransferDate(LocalDate.now());
        history.setPurchaseAmount(purchaseAmount);
        history.setPlateNumber(newPlate);
        historyRepository.save(history);

        return vehicleRepository.save(vehicle);
    }

    public Page<Vehicle> getVehiclesByOwner(Long ownerId, Pageable pageable) {
        VehicleOwner owner = ownerService.getOwnerById(ownerId);
        return vehicleRepository.findByCurrentOwner(owner, pageable);
    }

    public List<OwnershipHistory> getVehicleHistory(String chassisOrPlate) {
        Optional<Vehicle> byChassis = vehicleRepository.findByChassisNumber(chassisOrPlate);
        if (byChassis.isPresent()) {
            return historyRepository.findByVehicleOrderByTransferDateDesc(byChassis.get());
        }

        Optional<PlateNumber> byPlate = plateNumberRepository.findByPlateNumber(chassisOrPlate);
        if (byPlate.isPresent()) {
            Optional<Vehicle> vehicle = vehicleRepository.findByPlateNumber(byPlate.get());
            if (vehicle.isPresent()) {
                return historyRepository.findByVehicleOrderByTransferDateDesc(vehicle.get());
            }
        }

        throw new ResourceNotFoundException("Vehicle not found with provided identifier");
    }
}