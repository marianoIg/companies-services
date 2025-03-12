package com.mariano.companies.integration;

import com.mariano.companies.Fixture;
import com.mariano.companies.api.v1.mapper.CompaniesMapper;
import com.mariano.companies.api.v1.mapper.CompaniesTransfersMapper;
import com.mariano.companies.domain.service.CompaniesService;
import com.mariano.companies.domain.service.CompaniesTransfersService;
import com.mariano.companies.infrastructure.data.mapper.CompaniesExternalMapper;
import com.mariano.companies.infrastructure.data.repository.CompaniesRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CompaniesTransfersControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompaniesService companiesService;

    @Autowired
    private CompaniesTransfersService companiesTransfersService;

    @Autowired
    private CompaniesMapper companiesMapper;

    @Autowired
    private CompaniesTransfersMapper companiesTransfersMapper;

    @Autowired
    private CompaniesExternalMapper CompaniesExternalMapper;

    @Autowired
    private CompaniesRepository companiesRepository;

    @Autowired
    private com.mariano.companies.api.ApiAdvisor ApiAdvisor;

    @Test
    @SneakyThrows
    @SqlGroup({
            @Sql(value = Fixture.RESET_LAST_MONTH_TRANFERS_DATABASE_SCRIPT, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = Fixture.INIT_LAST_MONTH_TRANFERS_DATABASE_SCRIPT, executionPhase = BEFORE_TEST_METHOD),
    })
    @Transactional
    void getAllCreatedLastMonthCompanies() {
        mockMvc.perform(get("/api/companies/transfers/last-month")
                                .param("page", "0")
                                .param("size", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(4));
    }
}