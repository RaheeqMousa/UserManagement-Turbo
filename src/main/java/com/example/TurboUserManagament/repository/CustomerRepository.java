package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Customer;

public interface CustomerRepository {
    Customer save(Customer customer);
    Customer findByID(Long id);
}
