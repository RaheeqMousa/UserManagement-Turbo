package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Vehicle;
import com.example.TurboUserManagament.entity.VehicleVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleVerificationRepository extends JpaRepository<VehicleVerification, Long> {
    Optional<VehicleVerification> findById(Long id);
}
