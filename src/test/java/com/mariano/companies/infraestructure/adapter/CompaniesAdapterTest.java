package com.mariano.companies.infraestructure.adapter;

import com.mariano.companies.Fixture;
import com.mariano.companies.domain.Company;
import com.mariano.companies.domain.exceptions.CompaniesAlreadyExistsException;
import com.mariano.companies.domain.exceptions.CompaniesNotFoundException;
import com.mariano.companies.infrastructure.adapters.CompanyAdapter;
import com.mariano.companies.infrastructure.data.entity.CompanyEntity;
import com.mariano.companies.infrastructure.data.mapper.CompaniesExternalMapper;
import com.mariano.companies.infrastructure.data.repository.CompaniesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompaniesAdapterTest {

    @Mock
    private CompaniesRepository companiesRepository;

    @Mock
    private CompaniesExternalMapper mapper;

    @InjectMocks
    private CompanyAdapter companiesAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(companiesRepository);
        MockitoAnnotations.openMocks(mapper);
    }

    @Test
    void getCompaniesById_success() {
        var companyDomain = Fixture.getCompany();

        var companiesEntity = new CompanyEntity();
        companiesEntity.setId(1L);
        companiesEntity.setCuit(companyDomain.getCuit());
        companiesEntity.setBusinessName(companyDomain.getBusinessName());
        companiesEntity.setCreatedAt(companyDomain.getCreatedAt());

        when(companiesRepository.findById(any())).thenReturn(Optional.of(companiesEntity));
        when(mapper.toBusinessLayer(any(CompanyEntity.class))).thenReturn(companyDomain);


        Company found = companiesAdapter.getCompanyById(1L);

        assertNotNull(found);
        assertEquals("Enterprise S.R.L.", found.getBusinessName());
        verify(companiesRepository, times(1)).findById(1L);
    }

    @Test
    void updateCompanies_success() {
        var companyDomain = Fixture.getCompany();

        var companiesEntity = new CompanyEntity();
        companiesEntity.setId(1L);
        companiesEntity.setCuit(companyDomain.getCuit());
        companiesEntity.setBusinessName(companyDomain.getBusinessName());
        companiesEntity.setCreatedAt(companyDomain.getCreatedAt());

        when(companiesRepository.findById(1L)).thenReturn(Optional.of(companiesEntity));

        Company updatedCompany = Fixture.getCompany();
        Company updated = companiesAdapter.updateCompany(updatedCompany);

        verify(companiesRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void deleteCompanies_success() {
        var companyDomain = Fixture.getCompany();

        var companiesEntity = new CompanyEntity();
        companiesEntity.setId(1L);
        companiesEntity.setCuit(companyDomain.getCuit());
        companiesEntity.setBusinessName(companyDomain.getBusinessName());
        companiesEntity.setCreatedAt(companyDomain.getCreatedAt());

        when(companiesRepository.existsById(anyLong())).thenReturn(Boolean.TRUE);

        companiesAdapter.deleteCompany(1L);

        verify(companiesRepository, times(1)).deleteById(1L);
    }

    @Test
    void createCompanies_duplicate_throwsException() {
        var companyDomain = Fixture.getCompany();

        var companiesEntity = new CompanyEntity();
        companiesEntity.setId(1L);
        companiesEntity.setCuit(companyDomain.getCuit());
        companiesEntity.setBusinessName(companyDomain.getBusinessName());
        companiesEntity.setCreatedAt(companyDomain.getCreatedAt());

     //   when(mapper.toOuterLayer(any(Company.class))).thenReturn(companiesEntity);
     //   when(mapper.toBusinessLayer(any(CompanyEntity.class))).thenReturn(companyDomain);
        when(companiesRepository.existsByCuit(companyDomain.getCuit())).thenReturn(true);

        assertThrows(CompaniesAlreadyExistsException.class, () -> companiesAdapter.createCompany(companyDomain));

        verify(companiesRepository, never()).save(any(CompanyEntity.class));
    }

    @Test
    void getCompaniesById_notFound_throwsException() {
        when(companiesRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(CompaniesNotFoundException.class, () -> companiesAdapter.getCompanyById(999L));

        verify(companiesRepository, times(1)).findById(999L);
    }

    @Test
    void deleteCompanies_notFound_throwsException() {
        when(companiesRepository.existsById(999L)).thenReturn(Boolean.FALSE);

        assertThrows(CompaniesNotFoundException.class, () -> companiesAdapter.deleteCompany(999L));

        verify(companiesRepository, times(1)).existsById(999L);

    }
}