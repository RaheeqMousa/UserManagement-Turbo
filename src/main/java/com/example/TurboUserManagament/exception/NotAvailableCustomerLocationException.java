package com.example.TurboUserManagament.exception;

public class NotAvailableCustomerLocationException extends RuntimeException{
    public NotAvailableCustomerLocationException(){
    }
    public NotAvailableCustomerLocationException(String message){
        super(message);
    }
}