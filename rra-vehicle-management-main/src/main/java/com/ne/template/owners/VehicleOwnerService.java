package com.ne.template.owners;

import com.ne.template.commons.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class VehicleOwnerService {

    private final VehicleOwnerRepository ownerRepository;

    public VehicleOwnerService(VehicleOwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public VehicleOwner registerOwner(@Valid VehicleOwnerDTO request) {
//        log.info("Registering new vehicle owner: {}", owner.getNationalId());
        if (ownerRepository.existsByNationalId(request.nationalId())) {
            throw new IllegalArgumentException("Owner with this national ID already exists");
        }
        VehicleOwner owner = new VehicleOwner();
        owner.setOwnerNames(request.ownerNames());
        owner.setNationalId(request.nationalId());
        owner.setPhoneNumber(request.phoneNumber());
        owner.setAddress(request.address());

        return ownerRepository.save(owner);
    }

    public Page<VehicleOwner> searchOwners(String searchTerm, Pageable pageable) {
        log.info("Searching owners with term: {}", searchTerm);
        return ownerRepository.findByNationalIdContainingOrPhoneNumberContaining(
                searchTerm, searchTerm, pageable);
    }

    public Page<VehicleOwner> getAllOwners(Pageable pageable) {
        log.info("Fetching all vehicle owners");
        return ownerRepository.findAll(pageable);
    }

    public VehicleOwner getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
    }
}