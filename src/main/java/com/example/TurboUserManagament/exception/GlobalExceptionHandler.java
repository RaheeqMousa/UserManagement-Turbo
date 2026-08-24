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
        String exceptionMessage= "Exception from Raheeq's app: "+ex.getMessage()+
                                    "\nException Stack Trace\n"+exceptionStackTrace(ex);

        slackNotifier.send(exceptionMessage);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionMessage);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex){
        String exceptionMessage= "Exception from Raheeq's app: "+ex.getMessage()+
                                    "\nException Stack Trace\n"+exceptionStackTrace(ex);

        slackNotifier.send(exceptionMessage);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionMessage);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExist(UserAlreadyExistException ex){
        String exceptionMessage= "Exception from Raheeq's app: "+ex.getMessage()+
                                    "\nException Stack Trace\n"+exceptionStackTrace(ex);

        slackNotifier.send(exceptionMessage);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionMessage);
    }

    @ExceptionHandler(NotAvailableCustomerLocationException.class)
    public ResponseEntity<String> handleNotAvailableCustomerLocation(NotAvailableCustomerLocationException ex){
        String exceptionMessage= "Exception from Raheeq's app: "+ex.getMessage()+
                "\nException Stack Trace\n"+exceptionStackTrace(ex);

        slackNotifier.send(exceptionMessage);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionMessage);
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<String> handleCustomerAlreadyExist(CustomerAlreadyExistException ex){
        String exceptionMessage= "Exception from Raheeq's app: "+ex.getMessage()+
                "\nException Stack Trace\n"+exceptionStackTrace(ex);

        slackNotifier.send(exceptionMessage);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionMessage);
    }

    @ExceptionHandler(DriverAlreadyExistException.class)
    public ResponseEntity<String> handleDriverAlreadyExist(DriverAlreadyExistException ex){
        String exceptionMessage= "Exception from Raheeq's app: "+ex.getMessage()+
                "\nException Stack Trace\n"+exceptionStackTrace(ex);

        slackNotifier.send(exceptionMessage);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
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
