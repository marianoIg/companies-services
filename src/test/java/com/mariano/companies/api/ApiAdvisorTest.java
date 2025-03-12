package com.mariano.companies.api;

import com.mariano.companies.domain.exceptions.CompaniesAlreadyExistsException;
import com.mariano.companies.domain.exceptions.CompaniesInvalidDataException;
import com.mariano.companies.domain.exceptions.CompaniesNotFoundException;
import com.mariano.companies.domain.exceptions.CompaniesError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApiAdvisorTest {

    @InjectMocks
    private ApiAdvisor apiAdvisor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleCompaniesNotFound() {
        String error= "No se encontró empresa con id: 1";
        CompaniesNotFoundException exception = new CompaniesNotFoundException(CompaniesError.COM_ERR_NEG_001, "1");
        ResponseEntity<Map<String, String>> response = apiAdvisor.handleCompaniesNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(CompaniesError.COM_ERR_NEG_001.getCode(), response.getBody().get("error_code"));
        assertEquals(error, response.getBody().get("error_message"));
    }

    @Test
    void testHandleInvalidData() {
        CompaniesInvalidDataException exception = new CompaniesInvalidDataException(CompaniesError.COM_ERR_NEG_005,"-1");
        ResponseEntity<Map<String, String>> response = apiAdvisor.handleInvalidData(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(CompaniesError.COM_ERR_NEG_005.getCode(), response.getBody().get("error_code"));
        assertEquals(CompaniesError.COM_ERR_NEG_005.getMessage(), response.getBody().get("error_message"));
    }

    @Test
    void testHandleCompaniesAlreadyExists() {
        CompaniesAlreadyExistsException exception = new CompaniesAlreadyExistsException(CompaniesError.COM_ERR_NEG_005, "Company exists");
        ResponseEntity<Map<String, String>> response = apiAdvisor.handleCompaniesAlreadyExists(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(CompaniesError.COM_ERR_NEG_005.getCode(), response.getBody().get("error_code"));
        assertEquals("Company exists", response.getBody().get("error_message"));
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Unexpected error");
        ResponseEntity<Map<String, String>> response = apiAdvisor.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(CompaniesError.COM_ERR_GEN_001.name(), response.getBody().get("error_code"));
        assertEquals("Unexpected error", response.getBody().get("error_message"));
    }

    @Test
    void testHandleRequestParameterException() {
        MissingServletRequestParameterException exception = new MissingServletRequestParameterException("param", "String");
        ResponseEntity<Map<String, String>> response = apiAdvisor.handleRequestParameterException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(CompaniesError.COM_ERR_NEG_003.name(), response.getBody().get("error_code"));
        assertEquals(exception.getMessage(), response.getBody().get("error_message"));
    }

    @Test
    void testHandleDataIntegrityViolationException() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Integrity error");
        ResponseEntity<Map<String, String>> response = apiAdvisor.handleDataIntegrityViolationException(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(CompaniesError.COM_ERR_NEG_004.name(), response.getBody().get("error_code"));
        assertEquals("Integrity error", response.getBody().get("error_message"));
    }
}