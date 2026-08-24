package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.record.PhoneNumber;

public interface UserRepository {
    User findByPhoneNumber(PhoneNumber number);
}
