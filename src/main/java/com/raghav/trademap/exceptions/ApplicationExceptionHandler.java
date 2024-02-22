package com.raghav.trademap.exceptions;

import com.raghav.trademap.exceptions.ResourceNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.time.format.DateTimeParseException;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

    @Data
    static class Response{
        private String error;
        private String message;

        public Response(String error, String message){
            this.error = error;
            this.message = message;
        }
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> applicationExceptionHandler(RuntimeException e){
        return ResponseEntity.internalServerError().body(new Response(HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage()));
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
