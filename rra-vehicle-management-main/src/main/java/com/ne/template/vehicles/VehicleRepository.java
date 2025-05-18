package com.ne.template.vehicles;

import com.ne.template.owners.PlateNumber;
import com.ne.template.owners.VehicleOwner;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByChassisNumber(String chassisNumber);
    Optional<Vehicle> findByPlateNumber(PlateNumber plateNumber);
    Page<Vehicle> findByCurrentOwner(VehicleOwner owner, Pageable pageable);

    boolean existsByChassisNumber(String chassisNumber);
}