package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Vehicle;

public interface VehicleRepository {
    Vehicle save(Vehicle vehicle);
    Vehicle findById(Long id);
}
