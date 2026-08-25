package com.example.TurboUserManagament.entity;

import com.example.TurboUserManagament.appenum.VehicleVerificationStatus;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
//@Entity
@Data
@Builder
public class VehicleVerification {
    private Long id;
    private Vehicle vehicle;
    private String fileURL;
    private LocalDate uploadedAt;
    private LocalDate verifiedAt;
    private VehicleVerificationStatus vehicleVerificationStatus;
    private String rejectionReason;
}
