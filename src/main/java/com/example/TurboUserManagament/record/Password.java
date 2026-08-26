package com.example.TurboUserManagament.record;

import jakarta.persistence.Embeddable;

@Embeddable
public record Password(String value) {
    public Password{
        if(value==null || value.trim().isEmpty()){
            throw new IllegalArgumentException("Password can't be empty");
        }
        if(value.length()<8){
            throw new IllegalArgumentException("Password's length must be equal or larger than 8");
        }
        if(!value.matches(".*\\d.*") ||
            !value.matches(".*[a-zA-Z].*") ||
            !value.matches(".*[^a-zA-Z0-9].*"))
            throw new IllegalArgumentException("Password must contain characters, digits, and special characters");
        }

}
