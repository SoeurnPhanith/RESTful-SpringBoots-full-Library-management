package com.example.library_management.exception;

import com.example.library_management.utils.APIRespone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors from DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Loop through all field errors
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors); // HTTP 400
    }

    //Handle Duplicate Exception
    @ExceptionHandler (DuplicateDataException.class)
    public ResponseEntity<APIRespone<String>> handleDuplicate(DuplicateDataException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new APIRespone<>(
                        false,
                        exception.getMessage(),
                        null
                )
        );
    }

    //handle Resource Not Found Exception (find data from database or entity not found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIRespone<String>> handleResourceNotFound(ResourceNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new APIRespone<>(
                        false,
                        exception.getMessage(),
                        null
                )
        );
    }

    //handle Generic Exception (Error code speng speng or khos type jg tv)
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<APIRespone<String>> handleGeneric(GenericException exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new APIRespone<>(
                        false,
                        exception.getMessage(),
                        null
                )
        );
    }
}



