package com.mariano.companies.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariano.companies.Fixture;
import com.mariano.companies.api.ApiAdvisor;
import com.mariano.companies.api.v1.mapper.CompaniesMapper;
import com.mariano.companies.api.v1.mapper.CompaniesTransfersMapper;
import com.mariano.companies.domain.exceptions.CompaniesAlreadyExistsException;
import com.mariano.companies.domain.service.CompaniesService;
import com.mariano.companies.domain.exceptions.CompaniesInvalidDataException;
import com.mariano.companies.domain.exceptions.CompaniesNotFoundException;
import com.mariano.companies.domain.exceptions.CompaniesError;
import com.mariano.companies.domain.service.CompaniesTransfersService;
import com.mariano.companies.infrastructure.data.mapper.CompaniesExternalMapper;
import com.mariano.companies.infrastructure.data.repository.CompaniesRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SqlGroup({
        @Sql(value = Fixture.RESET_DATABASE_SCRIPT, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class CompaniesControllerIntegrationTest {

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
    private ApiAdvisor ApiAdvisor;

    private static Long companiesId;

    @BeforeEach
    void setUp() throws Exception {
        String newCompanies = """
        {
            "businessName": "Empresa test SRL",
            "cuit": "30-99999999-1"
        }
        """;

        String response = mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCompanies))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        companiesId = jsonNode.get("id").asLong();
    }

    @Test
    @SneakyThrows
    @SqlGroup({
            @Sql(value = Fixture.RESET_DATABASE_SCRIPT, executionPhase = BEFORE_TEST_METHOD),
            @Sql(value = Fixture.INIT_LAST_MONTH_DATABASE_SCRIPT, executionPhase = BEFORE_TEST_METHOD),
    })
    @Transactional
    void getAllCreatedLastMonthCompanies() {
        mockMvc.perform(get("/api/companies/created-last-month")
                        .param("page", "0")
                        .param("size", "5")
                        //.param("sort", "name,asc")
                    )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3));
    }

    @Test
    @SneakyThrows
    void getCompaniesById() {
        mockMvc.perform(get("/api/companies/" + companiesId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.businessName").value("Empresa test SRL"))
                .andExpect(jsonPath("$.cuit").value("30-99999999-1"));
    }

    @Test
    @SneakyThrows
    void createCompany() {
        String newCompanies = """
        {
            "businessName": "Empresa unitest SRL",
            "cuit": "30-99999998-1"
        }
        """;

        mockMvc.perform(post("/api/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCompanies))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.businessName").value("Empresa unitest SRL"))
                .andExpect(jsonPath("$.cuit").value("30-99999998-1"));
    }

    @Test
    @SneakyThrows
    void updateCompany() {
        String updatedCompany = """
        {
            "businessName": "Empresa cambio nombre SRL",
            "cuit": "30-99999999-1"
        }
        """;

        mockMvc.perform(put("/api/companies/" + companiesId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.businessName").value("Empresa cambio nombre SRL"));
    }

    @Test
    @SneakyThrows
    void deleteCompany() {
        mockMvc.perform(delete("/api/companies/" + companiesId))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateCompany_NotFound() {
        String updatedCompany = """
        {
            "businessName": "Empresa cambio nombre SRL",
            "cuit": "30-99999988-1"
        }
        """;
        try {
            mockMvc.perform(put("/api/companies/999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(updatedCompany))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("No se encontró una empresa"));
            assertInstanceOf(CompaniesNotFoundException.class, e.getCause());
        }
    }

    @Test
    void deleteCompany_NotFound() {
        try {
            mockMvc.perform(delete("/api/companies/999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error_message").value("No se encontró empresa con id: 999"));
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("No se encontró empresa con id: 999"));
            assertInstanceOf(CompaniesNotFoundException.class, e.getCause());
        }
    }

    @Test
    void getCompaniesById_NotFound() {
        try {
            mockMvc.perform(get("/api/companies/999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.error_message").value("No se encontró empresa con id: 999"));
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("No se encontró una empresa"));
            assertInstanceOf(CompaniesNotFoundException.class, e.getCause());
        }
    }

    @Test
    void getCompaniesById_Negative_Value() {
        try {
            mockMvc.perform(get("/api/companies/-1"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.error_message").value("getById.id: El ID debe ser un número positivo mayor a 0"));
        } catch (Exception e) {
            assertTrue(e.getMessage().contains(CompaniesError.COM_ERR_NEG_005.getMessage()));
            assertInstanceOf(CompaniesInvalidDataException.class, e.getCause());
            if (e.getCause() instanceof CompaniesInvalidDataException companiesException) {
                assertEquals(CompaniesError.COM_ERR_NEG_005.getCode(), companiesException.getErrorType().getCode());
            }
        }
    }

    @Test
    void createCompanies_already_exist() {
        try {
            String newCompanies = """
            {
                "businessName": "Empresa cambio nombre SRL",
                "cuit": "30-99999999-1"
            }
            """;

            mockMvc.perform(post("/api/companies")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newCompanies))
                    .andExpect(status().isConflict());
        } catch (Exception e) {
            assertInstanceOf(CompaniesAlreadyExistsException.class, e.getCause());
        }
    }

    @Test

    void createCompanies_argument_not_valid() {
        try {
            String newCompanies = """
            {
                "businessName": "",
                "cuit": "30-99999999-1"
            }
            """;

            mockMvc.perform(post("/api/companies")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newCompanies))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            assertInstanceOf(CompaniesAlreadyExistsException.class, e.getCause());
        }
    }
}