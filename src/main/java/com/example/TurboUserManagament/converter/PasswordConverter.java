package com.example.TurboUserManagament.converter;

import com.example.TurboUserManagament.record.Password;
import com.example.TurboUserManagament.record.PhoneNumber;
import jakarta.persistence.AttributeConverter;

public class PasswordConverter implements AttributeConverter<Password,String> {
    @Override
    public String convertToDatabaseColumn(Password password) {
        return password == null ? null : password.value();
    }

    @Override
    public Password convertToEntityAttribute(String value) {
        return value==null? null:new Password(value);
    }
}
