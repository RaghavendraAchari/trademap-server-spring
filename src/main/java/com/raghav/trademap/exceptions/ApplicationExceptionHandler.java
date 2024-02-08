package com.raghav.trademap.exceptions;

import com.raghav.trademap.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> applicationExceptionHandler(){
        return ResponseEntity.internalServerError().body(new Object(){ final String message = "Internal server error"; });
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        System.out.println(e.getMessage());

        return ResponseEntity.notFound().build();
    }
}
