package com.mariano.companies.api.v1;

import com.mariano.companies.Fixture;
import com.mariano.companies.api.v1.mapper.CompaniesTransfersMapper;
import com.mariano.companies.domain.Company;
import com.mariano.companies.domain.service.CompaniesTransfersService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompaniesTransfersControllerTest {

    public static final String EXPECTED_BUSSINES_NAME = "Enterprise S.R.L.";

    @Mock
    private CompaniesTransfersMapper mapper = Mappers.getMapper(CompaniesTransfersMapper.class);

    @Mock
    private CompaniesTransfersService companiesTransfersService;

    @InjectMocks
    private CompaniesTransfersController companiesTransfersController;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(mapper);
        MockitoAnnotations.openMocks(companiesTransfersService);
        MockitoAnnotations.openMocks(companiesTransfersController);
    }

    @Test
    public void testGetAllCreatedLastMonth() {
        Page<Company> companiesPage = new PageImpl<>(Collections.singletonList(Fixture.getCompanyWithTransfers()));
        when(mapper.toOuterLayer(any(Company.class))).thenReturn(Fixture.getCompanyTransfersV1());
        when(companiesTransfersService.getCompaniesWithTransfersLastMonth(PageRequest.of(0, 5))).thenReturn(companiesPage);

        var result = companiesTransfersController.getCompaniesWithTransfersLastMonth(PageRequest.of(0, 5));

        assertNotNull(result);
        assertEquals(1, result.getBody().getTotalElements());
        assertEquals(EXPECTED_BUSSINES_NAME, result.getBody().getContent().getFirst().getBusinessName());
        assertNotNull(result.getBody().getContent().getFirst().getTransfers());
    }
}