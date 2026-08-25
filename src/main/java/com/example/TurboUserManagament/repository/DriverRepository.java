package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Driver;

public interface DriverRepository {
    Driver save(Driver driver);
    Driver findByID(Long id);
}
