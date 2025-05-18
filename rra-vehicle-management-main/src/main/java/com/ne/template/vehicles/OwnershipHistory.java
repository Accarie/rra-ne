package com.ne.template.vehicles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ne.template.owners.PlateNumber;
import com.ne.template.owners.VehicleOwner;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "ownerships")
public class OwnershipHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonBackReference
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private VehicleOwner owner;

    @Column(nullable = false)
    private LocalDate transferDate;
    @Column(nullable = false)
    private BigDecimal purchaseAmount;

    @ManyToOne
    @JoinColumn(name = "plate_number_id")
    private PlateNumber plateNumber;

}
