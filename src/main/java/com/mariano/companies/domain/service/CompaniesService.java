package com.mariano.companies.domain.service;

import com.mariano.companies.domain.common.CrudService;
import com.mariano.companies.domain.usecase.CreateCompanyUseCase;
import com.mariano.companies.domain.usecase.DeleteCompanyUseCase;
import com.mariano.companies.domain.usecase.GetCompanyUseCase;
import com.mariano.companies.domain.usecase.UpdateCompanyUseCase;
import com.mariano.companies.domain.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompaniesService implements CrudService<Company> {

    private final CreateCompanyUseCase createCompanyUseCase;
    private final DeleteCompanyUseCase deleteCompanyUseCase;
    private final GetCompanyUseCase    getCompanyUseCase;
    private final UpdateCompanyUseCase updateCompanyUseCase;

    @Override
    public Page<Company> getAll(Pageable pageable) {
        return getCompanyUseCase.getAll(pageable);
    }

    @Override
    public Company getById(Long id) {
        return getCompanyUseCase.getCompanyById(id);
    }

    @Override
    public Company create(Company request) {
        return createCompanyUseCase.createCompany(request);
    }

    @Override
    public Company update(Company request) {
        return updateCompanyUseCase.updateCompany(request);
    }

    @Override
    public void delete(Long id) {
        deleteCompanyUseCase.deleteCompany(id);
    }

    public Page<Company> getCompaniesCreatedLastMonth(Pageable pageable) {
        return getCompanyUseCase.getCompaniesCreatedLastMonth(pageable);
    }
}