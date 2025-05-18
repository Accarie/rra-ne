package com.ne.template.owners;

import com.ne.template.commons.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class PlateNumberService {

    private final PlateNumberRepository plateNumberRepository;
    private final VehicleOwnerService ownerService;

    public PlateNumberService(PlateNumberRepository plateNumberRepository, VehicleOwnerService ownerService) {
        this.plateNumberRepository = plateNumberRepository;
        this.ownerService = ownerService;
    }

    public PlateNumber registerPlateNumber(VehicleOwner owner, PlateNumberDTO request) {
//        log.info("Registering plate number {} for owner {}", plateNumber.getPlateNumber(), ownerId);
//        VehicleOwner owner = ownerService.getOwnerById(ownerId);
//        plateNumber.setOwner(owner);
//        plateNumber.setStatus(PlateStatus.AVAILABLE);
//        return plateNumberRepository.save(plateNumber);

        if (plateNumberRepository.existsByPlateNumber(request.plateNumber())) {
            throw new BadRequestException("Plate number already exists");

        }

        PlateNumber plate = new PlateNumber();
        plate.setPlateNumber(request.plateNumber());
        plate.setIssuedDate(request.issuedDate());
        plate.setStatus(PlateStatus.AVAILABLE); // Default status
        plate.setOwner(owner);

        return plateNumberRepository.save(plate);


    }

    public Page<PlateNumber> getPlateNumbersByOwner(Long ownerId, Pageable pageable) {
        VehicleOwner owner = ownerService.getOwnerById(ownerId);
        return plateNumberRepository.findByOwner(owner, pageable);
    }

    public PlateNumber getAvailablePlateNumber(Long ownerId) {
        VehicleOwner owner = ownerService.getOwnerById(ownerId);
        List<PlateNumber> availablePlates = plateNumberRepository.findByOwnerAndStatus(
                owner, PlateStatus.AVAILABLE);
        if (availablePlates.isEmpty()) {
            throw new IllegalStateException("No available plate numbers for this owner");
        }
        return availablePlates.get(0);
    }

    public PlateNumber getPlateNumberById(Long plateNumberId) {
        return plateNumberRepository.findById(plateNumberId).orElse(null);
    }
}
