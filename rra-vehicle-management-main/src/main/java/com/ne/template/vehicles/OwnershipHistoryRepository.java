package com.ne.template.vehicles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnershipHistoryRepository extends JpaRepository<OwnershipHistory, Long> {
    List<OwnershipHistory> findByVehicleOrderByTransferDateDesc(Vehicle vehicle);
}
