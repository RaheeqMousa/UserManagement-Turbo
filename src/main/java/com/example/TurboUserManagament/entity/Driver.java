package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.DriverAvailability;
import com.example.TurboUserManagament.appenum.DriverStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
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

    private List<Vehicle> vehicle;
}
