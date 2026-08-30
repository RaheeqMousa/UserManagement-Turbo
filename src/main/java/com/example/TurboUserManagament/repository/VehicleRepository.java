package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findById(Long id);
    List<Vehicle> findByDriverId(Long driverId);
}
