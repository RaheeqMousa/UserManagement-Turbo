package com.example.TurboUserManagament.exception;

public class VendorAlreadyExistException extends RuntimeException {
    public VendorAlreadyExistException(){}

    public VendorAlreadyExistException(String message) {
        super(message);
    }
}
