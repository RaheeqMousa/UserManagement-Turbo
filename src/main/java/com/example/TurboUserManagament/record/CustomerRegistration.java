package com.example.TurboUserManagament.record;

import java.time.LocalDate;

public record CustomerRegistration(
        String firstName,
        String lastName,
        PhoneNumber phoneNumber,
        String password,
        LocalDate birthDate
) {
}
