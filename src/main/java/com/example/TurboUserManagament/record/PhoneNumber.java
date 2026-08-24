package com.example.TurboUserManagament.record;

public record PhoneNumber(String value) {
    public PhoneNumber{
        if(value==null || value.trim().isEmpty()){
            throw new IllegalArgumentException("Phone number can't be empty");
        }

        String OOREDOO="56";
        String JAWWAL="59";

        if (!value.matches("^\\+(970|972)("+ OOREDOO +"|"+ JAWWAL +")\\d{7}$")){
            throw new IllegalArgumentException("Invalid Palestinian Phone Number");
        }
    }
}
