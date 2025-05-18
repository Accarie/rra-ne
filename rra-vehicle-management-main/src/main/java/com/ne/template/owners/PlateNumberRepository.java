package com.ne.template.owners;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PlateNumberRepository extends JpaRepository<PlateNumber, Long> {
    @Override
    Optional<PlateNumber> findById(Long id);
    Page<PlateNumber> findByOwner(VehicleOwner owner, Pageable pageable);
    List<PlateNumber> findByOwnerAndStatus(VehicleOwner owner, PlateStatus status);

    Optional<PlateNumber> findByPlateNumber(String plateNumber);

    boolean existsByPlateNumber(@NotBlank @Pattern(regexp = "^[A-Z]{2,3}\\d{3}[A-Z]$", message = "Invalid plate number format") String s);
}
