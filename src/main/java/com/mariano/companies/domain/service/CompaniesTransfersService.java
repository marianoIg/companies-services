package com.mariano.companies.domain.service;

import com.mariano.companies.domain.Company;
import com.mariano.companies.domain.usecase.GetTransfersUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompaniesTransfersService {

    private final GetTransfersUseCase getTransfersUseCase;

    public Page<Company> getCompaniesWithTransfersLastMonth(Pageable pageable) {
        return getTransfersUseCase.getCompaniesWithTransfersLastMonth(pageable);
    }
}
