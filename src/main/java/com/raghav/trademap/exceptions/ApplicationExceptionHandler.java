package com.raghav.trademap.exceptions;

import com.raghav.trademap.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@Slf4j
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

    @ExceptionHandler(value = DateTimeParseException.class)
    public ResponseEntity<?> dateTimeFormatException(){
        log.info("DateTimeFormat Exception raised");

        return ResponseEntity.badRequest().body("Date must follow 'yyyy-MM-dd' format");
    }
}
