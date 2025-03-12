package com.mariano.companies.infrastructure.adapters;

import com.mariano.companies.domain.usecase.CreateCompanyUseCase;
import com.mariano.companies.domain.usecase.DeleteCompanyUseCase;
import com.mariano.companies.domain.usecase.GetCompanyUseCase;
import com.mariano.companies.domain.usecase.UpdateCompanyUseCase;
import com.mariano.companies.domain.Company;
import com.mariano.companies.domain.exceptions.CompaniesAlreadyExistsException;
import com.mariano.companies.domain.exceptions.CompaniesNotFoundException;
import com.mariano.companies.domain.exceptions.CompaniesError;
import com.mariano.companies.infrastructure.data.mapper.CompaniesExternalMapper;
import com.mariano.companies.infrastructure.data.repository.CompaniesRepository;
import com.mariano.companies.infrastructure.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompanyAdapter implements GetCompanyUseCase, CreateCompanyUseCase, UpdateCompanyUseCase, DeleteCompanyUseCase {

    private final CompaniesRepository companiesRepository;
    private final CompaniesExternalMapper mapper;

    @Override
    public Company createCompany(Company company) {
       if (companiesRepository.existsByCuit(company.getCuit())) {
            String errorMessage = "Ya existe una empresa con cuit: " + company.getCuit() ;
            throw new CompaniesAlreadyExistsException(CompaniesError.COM_ERR_NEG_002,errorMessage
            );
        }
        var companyEntity = mapper.toOuterLayer(company);
        companyEntity.setCreatedAt(LocalDateTime.now());
        return mapper.toBusinessLayer(companiesRepository.saveAndFlush(companyEntity));
    }

    @Override
    public void deleteCompany(Long id) {
        if (!companiesRepository.existsById(id)) {
            throw new CompaniesNotFoundException(CompaniesError.COM_ERR_NEG_001, id.toString());
        }
        companiesRepository.deleteById(id);
    }

    @Override
    public Page<Company> getAll(Pageable pageRequest) {
        return mapper.toBusinessLayer(companiesRepository.findAll(pageRequest));
    }

    @Override
    @Cacheable(value = "companyById", key = "#id")
    public Company getCompanyById(Long id) {
        return companiesRepository.findById(id)
                .map(mapper::toBusinessLayer)
                .orElseThrow(() -> new CompaniesNotFoundException(CompaniesError.COM_ERR_NEG_001, id.toString()));    }

    @Override
    public Page<Company> getCompaniesCreatedLastMonth(Pageable pageRequest) {
        LocalDateTime firstDayOfLastMonth = DateUtils.getFirstDayOfLastMonth();
        LocalDateTime lastDayOfLastMonth = DateUtils.getLastDayOfLastMonth();
        return mapper.toBusinessLayer(companiesRepository.findAllCompaniesByCreatedAtBetween(firstDayOfLastMonth,lastDayOfLastMonth,pageRequest));
    }

    @Override
    public Company updateCompany(Company company) {
        var existingCompany = companiesRepository.findById(company.getId());
        if (existingCompany.isEmpty()) {
            throw new CompaniesNotFoundException(CompaniesError.COM_ERR_NEG_001, company.getId().toString());
        }

        var companyEntity = mapper.toOuterLayer(company);
        return mapper.toBusinessLayer(companiesRepository.saveAndFlush(companyEntity));
    }
}