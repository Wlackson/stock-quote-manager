package com.inatel.stockquotemanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {

        HttpStatus requestStatus;

        switch (e.getCause().getMessage()) {
            case "200": requestStatus = HttpStatus.OK; break;
            case "400": requestStatus = HttpStatus.BAD_REQUEST; break;
            case "404": requestStatus = HttpStatus.NOT_FOUND; break;
            case "405": requestStatus = HttpStatus.METHOD_NOT_ALLOWED; break;
            default: requestStatus = HttpStatus.INTERNAL_SERVER_ERROR; break;
        }

        ApiException apiException = new ApiException(
                ZonedDateTime.now(ZoneId.of("Z")),
                e.getCause().getMessage(),
                e.getMessage()
        );

        return new ResponseEntity<>(apiException, requestStatus);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
