package com.example.TurboUserManagament.converter;

import com.example.TurboUserManagament.record.PhoneNumber;
import jakarta.persistence.AttributeConverter;

public class PhoneNumberConverter implements AttributeConverter<PhoneNumber, String> {

    @Override
    public String convertToDatabaseColumn(PhoneNumber phoneNumber) {
        return phoneNumber == null ? null : phoneNumber.value();
    }

    @Override
    public PhoneNumber convertToEntityAttribute(String value) {
        return value==null? null:new PhoneNumber(value);
    }
}
