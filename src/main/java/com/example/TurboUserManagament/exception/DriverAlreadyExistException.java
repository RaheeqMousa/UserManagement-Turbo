package com.example.TurboUserManagament.exception;

public class DriverAlreadyExistException extends RuntimeException{
    public DriverAlreadyExistException(){
    }
    public DriverAlreadyExistException(String message){
        super(message);
    }
}