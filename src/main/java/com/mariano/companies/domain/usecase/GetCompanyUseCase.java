package com.mariano.companies.domain.usecase;

import com.mariano.companies.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCompanyUseCase {

    Page<Company> getAll(Pageable pageRequest);

    Company getCompanyById(Long id);

    Page<Company> getCompaniesCreatedLastMonth(Pageable pageable);
}
