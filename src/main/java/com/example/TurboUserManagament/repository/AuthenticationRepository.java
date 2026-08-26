package com.example.TurboUserManagament.repository;

import com.example.TurboUserManagament.entity.AuthenticationAccount;

public interface AuthenticationRepository {

    AuthenticationAccount save(AuthenticationAccount account);
}
