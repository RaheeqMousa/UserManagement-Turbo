package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.VehicleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="driver_id", nullable = false)
    private Driver driver;

    private String model;
    private String type;
    private String color;

    @Column(name="plate_number")
    private String plateNumber;

    private VehicleStatus status;

    @OneToMany(
            mappedBy = "vehicle",
            fetch=FetchType.LAZY,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<VehicleVerification> verifications;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", verifications=" + verifications +
                '}';
    }
}
