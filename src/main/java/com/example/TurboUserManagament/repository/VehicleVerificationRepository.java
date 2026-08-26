package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.VehicleVerification;

public interface VehicleVerificationRepository {
    VehicleVerification save(
            VehicleVerification verification
    );

    VehicleVerification findById(Long id);
}
