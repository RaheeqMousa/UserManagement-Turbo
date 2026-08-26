package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.DriverAvailability;
import com.example.TurboUserManagament.appenum.DriverStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Driver{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false, unique = true)
    private User user;

    private String identityNumber;
    private String licenseNumber;
    private LocalDate licenseExpiryDate;

    @Enumerated(EnumType.STRING)
    private DriverAvailability driverAvailability;
    @Enumerated(EnumType.STRING)
    private DriverStatus driverStatus;

    @OneToMany(mappedBy = "driver",
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

    private Double latitude;
    private Double longitude;
    @Column(name = "last_location_update")
    private LocalDateTime lastLocationUpdate;

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", identityNumber='" + identityNumber + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licenseExpiryDate=" + licenseExpiryDate +
                ", driverAvailability=" + driverAvailability +
                ", driverStatus=" + driverStatus +
                ", vehicles=" + vehicles +
                '}';
    }
}
