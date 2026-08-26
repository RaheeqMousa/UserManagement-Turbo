package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.User;
import com.example.TurboUserManagament.record.PhoneNumber;

public interface UserRepository {
    User save(User user);
    User findByPhoneNumber(PhoneNumber number);
}
