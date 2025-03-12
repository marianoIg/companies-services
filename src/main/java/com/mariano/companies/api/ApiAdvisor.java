package com.mariano.companies.api;

import com.mariano.companies.domain.exceptions.CompaniesInvalidDataException;
import com.mariano.companies.domain.exceptions.CompaniesAlreadyExistsException;
import com.mariano.companies.domain.exceptions.CompaniesNotFoundException;
import com.mariano.companies.domain.exceptions.CompaniesError;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class ApiAdvisor {
    private static final String ERROR_CODE= "error_code";
    private static final String ERROR_MESSAGE= "error_message";
    private static final String ERROR_DETAIL= "error_details";
    private static final String ERROR_FIELD= "error_field";

    @ExceptionHandler(CompaniesNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCompaniesNotFound(CompaniesNotFoundException ex) {
        logErrorMessages(ex);
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_CODE, ex.getErrorType().getCode());
        response.put(ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CompaniesInvalidDataException.class)
    public ResponseEntity<Map<String, String>> handleInvalidData(CompaniesInvalidDataException ex) {
        logErrorMessages(ex);
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_CODE, ex.getErrorType().getCode());
        response.put(ERROR_MESSAGE, ex.getErrorType().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CompaniesAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleCompaniesAlreadyExists(CompaniesAlreadyExistsException ex) {
        logErrorMessages(ex);
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_CODE, ex.getErrorType().getCode());
        response.put(ERROR_MESSAGE, ex.getValue());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logErrorMessages(ex);
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put(ERROR_FIELD, error.getField());
            errorDetails.put(ERROR_MESSAGE, error.getDefaultMessage());
            errors.add(errorDetails);
        });

        response.put(ERROR_CODE, CompaniesError.COM_ERR_NEG_003);
        response.put(ERROR_MESSAGE, CompaniesError.COM_ERR_NEG_003.getMessage());
        response.put(ERROR_DETAIL, errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logErrorMessages(ex);
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_CODE, CompaniesError.COM_ERR_GEN_001.name());
        response.put(ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleRequestParameterException(Exception ex) {
        logErrorMessages(ex);
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_CODE, CompaniesError.COM_ERR_NEG_003.name());
        response.put(ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        logErrorMessages(ex);
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_CODE, CompaniesError.COM_ERR_NEG_004.name());
        response.put(ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error_message", ex.getMessage());
        response.put("error_code", CompaniesError.COM_ERR_NEG_003.name());
        return ResponseEntity.badRequest().body(response);
    }

    private void logErrorMessages(Exception ex) {
        log.error(ex.getMessage());
    }
}