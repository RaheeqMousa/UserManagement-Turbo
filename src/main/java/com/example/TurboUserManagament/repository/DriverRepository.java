package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Driver findByID(Long id);
}
