package com.mariano.companies.api.v1;

import com.mariano.companies.Fixture;
import com.mariano.companies.api.v1.dto.CompanyV1;
import com.mariano.companies.api.v1.mapper.CompaniesMapper;
import com.mariano.companies.domain.service.CompaniesService;
import com.mariano.companies.domain.Company;
import com.mariano.companies.domain.exceptions.CompaniesAlreadyExistsException;
import com.mariano.companies.domain.exceptions.CompaniesInvalidDataException;
import com.mariano.companies.domain.exceptions.CompaniesNotFoundException;
import com.mariano.companies.domain.exceptions.CompaniesError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompaniesControllerTest {

    public static final String EXPECTED_BUSSINES_NAME = "Enterprise S.R.L.";
    public static final String EXPECTED_CUIT = "30-99999999-9";
    @Mock
    private CompaniesMapper mapper = Mappers.getMapper(CompaniesMapper.class);

    @Mock
    private CompaniesService companiesService;

    @InjectMocks
    private CompaniesController companiesController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(mapper);
        MockitoAnnotations.openMocks(companiesService);
        MockitoAnnotations.openMocks(companiesController);
    }

    @Test
    public void testGetAllCreatedLastMonth() {
        Page<Company> companiesPage = new PageImpl<>(Collections.singletonList(Fixture.getCompany()));
        when(mapper.toOuterLayer(any(Company.class))).thenReturn(Fixture.getCompanyV1());
        when(companiesService.getAll(PageRequest.of(0, 5))).thenReturn(companiesPage);

        var result = companiesController.getAll(PageRequest.of(0, 5));

        assertNotNull(result);
        assertEquals(1, result.getBody().getTotalElements());
        assertEquals(EXPECTED_BUSSINES_NAME, result.getBody().getContent().getFirst().getBusinessName());
    }

    @Test
    public void testGetById() {
        when(companiesService.getById(1L)).thenReturn(Fixture.getCompany());
        when(mapper.toOuterLayer(any(Company.class))).thenReturn(Fixture.getCompanyV1());

        var result = companiesController.getById(1L);

        assertNotNull(result);
        assertEquals(EXPECTED_CUIT, result.getBody().getCuit());
        assertEquals(EXPECTED_BUSSINES_NAME, result.getBody().getBusinessName());
    }

    @Test
    public void testCreate() {
        when(companiesService.create(any(Company.class))).thenReturn(Fixture.getCompany());
        when(mapper.toBusinessLayer(any(CompanyV1.class))).thenReturn(Fixture.getCompany());
        when(mapper.toOuterLayer(any(Company.class))).thenReturn(Fixture.getCompanyV1());

        var result = companiesController.create(Fixture.getCompanyV1());

        assertNotNull(result);
        assertEquals(EXPECTED_BUSSINES_NAME, result.getBody().getBusinessName());
        assertEquals(EXPECTED_CUIT, result.getBody().getCuit());
        verify(companiesService, times(1)).create(Fixture.getCompany());
    }

    @Test
    public void testUpdate() {
        when(companiesService.update(any(Company.class))).thenReturn(Fixture.getCompany());
        when(mapper.toBusinessLayer(any(CompanyV1.class))).thenReturn(Fixture.getCompany());
        when(mapper.toOuterLayer(any(Company.class))).thenReturn(Fixture.getCompanyV1());

        var result = companiesController.update(1L,Fixture.getCompanyV1());

        assertNotNull(result);
        assertEquals(EXPECTED_BUSSINES_NAME, result.getBody().getBusinessName());
        assertEquals(EXPECTED_CUIT, result.getBody().getCuit());
        verify(companiesService, times(1)).update(Fixture.getCompany());
    }

    @Test
    public void testDelete() {
        companiesController.delete(1L);
        verify(companiesService, times(1)).delete(1L);
    }

    @Test
    public void testGetById_NotFound() {
        when(companiesService.getById(1L)).thenThrow(new CompaniesNotFoundException(CompaniesError.COM_ERR_NEG_001,"1"));

        assertThrows(CompaniesNotFoundException.class, () -> companiesController.getById(1L));

        verify(companiesService, times(1)).getById(1L);
    }

    @Test
    public void testCreate_AlreadyExists() {
        when(mapper.toBusinessLayer(any(CompanyV1.class))).thenReturn(Fixture.getCompany());

        when(companiesService.create(any(Company.class))).thenThrow(new CompaniesAlreadyExistsException(CompaniesError.COM_ERR_NEG_005,"-2"));

        assertThrows(CompaniesAlreadyExistsException.class, () -> companiesController.create(Fixture.getCompanyV1()));

        verify(companiesService, times(1)).create(any(Company.class));
    }

    @Test
    public void testCreate_InvalidData() {
        when(mapper.toBusinessLayer(any(CompanyV1.class))).thenReturn(Fixture.getCompany());

        when(companiesService.create(any(Company.class))).thenThrow(new CompaniesInvalidDataException(CompaniesError.COM_ERR_NEG_005,"-2"));

        assertThrows(CompaniesInvalidDataException.class, () -> companiesController.create(Fixture.getCompanyV1()));

        verify(companiesService, times(1)).create(any(Company.class));
    }

    @Test
    public void testUpdate_NotFound() {
        when(mapper.toBusinessLayer(any(CompanyV1.class))).thenReturn(Fixture.getCompany());
        when(companiesService.update(any(Company.class))).thenThrow(new CompaniesNotFoundException(CompaniesError.COM_ERR_GEN_001,"2"));

        assertThrows(CompaniesNotFoundException.class, () -> companiesController.update(Fixture.getCompanyV1_not_valid()));

        verify(companiesService, times(1)).update(any(Company.class));
    }

    @Test
    public void testDelete_NotFound() {
        doThrow(new CompaniesNotFoundException(CompaniesError.COM_ERR_GEN_001,"2")).when(companiesService).delete(1L);

        assertThrows(CompaniesNotFoundException.class, () -> companiesController.delete(1L));

        verify(companiesService, times(1)).delete(1L);
    }
}