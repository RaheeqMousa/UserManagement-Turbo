package com.example.TurboUserManagament.record;

import com.example.TurboUserManagament.appenum.AddressType;
import com.example.TurboUserManagament.appenum.VendorType;
import com.example.TurboUserManagament.entity.Address;

public record VendorRegistration(
        String firstName,
        String lastName,
        PhoneNumber phoneNumber,
        Password password,

        String identityNumber,

        Double averageReview,
        String placeName,
        VendorType vendorType,

        String city,
        String street,
        Double latitude,
        Double longitude
        ) {
}
