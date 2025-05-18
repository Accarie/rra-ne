package com.ne.template.vehicles;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ne.template.owners.PlateNumber;
import com.ne.template.owners.VehicleOwner;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Data
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String chassisNumber;
    @Column(nullable = false)
    private String manufacturer;
    @Column(nullable = false)
    private Integer manufactureYear;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String modelName;

    @ManyToOne
    @JoinColumn(name = "current_owner_id")
    private VehicleOwner currentOwner;

    @ManyToOne
    @JoinColumn(name = "plate_number_id")
    private PlateNumber plateNumber;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OwnershipHistory> ownershipHistory;

}
