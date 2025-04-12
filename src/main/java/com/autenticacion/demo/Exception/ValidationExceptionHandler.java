package com.autenticacion.demo.Exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.autenticacion.demo.Dto.ErrorsDTO;

@RestControllerAdvice
public class ValidationExceptionHandler {

    private List<ErrorsDTO> extractErrors(Exception ex) {
        List<ErrorsDTO> errors = new ArrayList<>();
        if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            errors = methodArgumentNotValidException.getBindingResult().getAllErrors().stream().map(error -> {
                String errorMessage = error.getDefaultMessage();
                String path = (error instanceof FieldError) ? ((FieldError) error).getField() : "global";
                return new ErrorsDTO(path, errorMessage);
            }).toList();
        } else if (ex instanceof WebExchangeBindException webExchangeBindException) {
            errors = webExchangeBindException.getFieldErrors().stream().map(fieldError -> {
                String errorMessage = fieldError.getDefaultMessage();
                String path = fieldError.getField();
                return new ErrorsDTO(path, errorMessage);
            }).toList();
        }
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class, WebExchangeBindException.class })
    public ResponseEntity<StandarizedApiExceptionResponse> handleValidationExceptions(Exception ex) {
        List<ErrorsDTO> errors = extractErrors(ex);
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse(400, 1, "Error en la validación",
                errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
