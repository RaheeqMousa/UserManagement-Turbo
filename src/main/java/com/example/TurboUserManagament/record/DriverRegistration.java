package com.example.TurboUserManagament.record;

import java.time.LocalDate;

public record DriverRegistration(
        String firstName,
        String lastName,
        PhoneNumber phoneNumber,
        String password,

        String identityNumber,
        String licenseNumber,
        LocalDate licenseExpiryDate,

        String vehicleModel,
        String vehicleType,
        String vehicleColor,
        String vehiclePlateNumber,

        String verificationFileURL)
{

}
