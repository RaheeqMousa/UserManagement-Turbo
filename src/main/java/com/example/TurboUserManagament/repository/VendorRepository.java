package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Vendor;

public interface VendorRepository {
    Vendor save(Vendor vendor);
    Vendor findById(Long id);
}
