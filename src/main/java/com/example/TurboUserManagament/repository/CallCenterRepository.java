package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.CallCenterAgent;
import com.example.TurboUserManagament.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallCenterRepository extends JpaRepository<CallCenterAgent, Long> {
    CallCenterAgent findByID(Long id);
}
