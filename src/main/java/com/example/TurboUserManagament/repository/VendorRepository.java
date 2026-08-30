package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> findById(Long id);
}
