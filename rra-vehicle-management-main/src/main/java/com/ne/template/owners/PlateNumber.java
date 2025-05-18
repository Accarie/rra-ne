package com.ne.template.owners;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
@Table(name = "plate_numbers")
public class PlateNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String plateNumber;
    @Column(nullable = false)
    private LocalDate issuedDate;

    @Enumerated(EnumType.STRING)
    private PlateStatus status;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private VehicleOwner owner;

}
