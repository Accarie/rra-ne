package com.ne.template.owners;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, Long> {
    Page<VehicleOwner> findByNationalIdContaining(String nationalId, Pageable pageable);
    Page<VehicleOwner> findByPhoneNumberContaining(String phoneNumber, Pageable pageable);
    Optional<VehicleOwner> findByNationalId(String nationalId);
    boolean existsByNationalId(String nationalId);
    Page<VehicleOwner> findByNationalIdContainingOrPhoneNumberContaining(String nationalId, String phoneNumber, Pageable pageable);
}
