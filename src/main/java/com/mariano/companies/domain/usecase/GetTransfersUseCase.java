package com.mariano.companies.domain.usecase;

import com.mariano.companies.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetTransfersUseCase {

    Page<Company> getCompaniesWithTransfersLastMonth(Pageable pageable);
}
