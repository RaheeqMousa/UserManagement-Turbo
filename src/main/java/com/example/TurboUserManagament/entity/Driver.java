package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.DriverAvailability;
import com.example.TurboUserManagament.appenum.DriverStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
public class Driver{
    private Long id;
    private User user;

    private String identityNumber;
    private String licenseNumber;
    private LocalDate licenseExpiryDate;

    private DriverAvailability driverAvailability;
    private DriverStatus driverStatus;

    private List<Vehicle> vehicles;

    private Double latitude;
    private Double longitude;
    private LocalDateTime lastLocationUpdate;

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", user ID=" + user.getId() +
                ", identityNumber='" + identityNumber + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licenseExpiryDate=" + licenseExpiryDate +
                ", driverAvailability=" + driverAvailability +
                ", driverStatus=" + driverStatus +
                ", vehicles=" + vehicles +
                '}';
    }
}
