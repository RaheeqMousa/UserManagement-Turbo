package com.example.TurboUserManagament.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final SlackNotifier slackNotifier;

    public GlobalExceptionHandler(SlackNotifier notifier){
        this.slackNotifier=notifier;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex){
        return getResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex){
        return getResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExist(UserAlreadyExistException ex){
        return getResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotAvailableCustomerLocationException.class)
    public ResponseEntity<String> handleNotAvailableCustomerLocation(NotAvailableCustomerLocationException ex){
        return getResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<String> handleCustomerAlreadyExist(CustomerAlreadyExistException ex){
        return getResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DriverAlreadyExistException.class)
    public ResponseEntity<String> handleDriverAlreadyExist(DriverAlreadyExistException ex){
        return getResponse(ex, HttpStatus.CONFLICT);
    }

    public ResponseEntity<String> handleVendorAlreadyExist(VendorAlreadyExistException ex){
        return getResponse(ex, HttpStatus.CONFLICT);
    }

    private ResponseEntity<String> getResponse(Exception ex, HttpStatus status){
        String exceptionMessage= "Exception from Raheeq's app: "+ex.getMessage()+
                "\nException Stack Trace\n"+exceptionStackTrace(ex);

        slackNotifier.send(exceptionMessage);
        return ResponseEntity
                .status(status)
                .body(exceptionMessage);
    }

    private String exceptionStackTrace(Exception exc){
        String stackTrace = Arrays.stream(exc.getStackTrace())
                .filter(el -> el.getClassName().startsWith("com.example"))
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
        return stackTrace;
    }
}
