package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByID(Long id);
}
