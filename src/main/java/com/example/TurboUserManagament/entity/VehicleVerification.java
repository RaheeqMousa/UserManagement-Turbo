package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.VehicleVerificationStatus;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
@Data
public class VehicleVerification {
    private Long id;
    private Vehicle vehicle;
    private String fileURL;
    private LocalDate uploadedAt;
    private LocalDate verifiedAt;
    private VehicleVerificationStatus vehicleVerificationStatus;
}
