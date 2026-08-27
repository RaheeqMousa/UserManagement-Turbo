package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Vehicle;

import java.util.List;

public interface VehicleRepository {
    Vehicle save(Vehicle vehicle);
    Vehicle findById(Long id);
    List<Vehicle> findByDriverId(Long driverId);
}
