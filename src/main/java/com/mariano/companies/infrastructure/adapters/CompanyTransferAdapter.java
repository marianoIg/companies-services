package com.mariano.companies.infrastructure.adapters;

import com.mariano.companies.domain.Company;
import com.mariano.companies.domain.usecase.GetTransfersUseCase;
import com.mariano.companies.infrastructure.data.mapper.CompaniesExternalMapper;
import com.mariano.companies.infrastructure.data.repository.CompaniesRepository;
import com.mariano.companies.infrastructure.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompanyTransferAdapter implements GetTransfersUseCase {

    private final CompaniesRepository companiesRepository;
    private final CompaniesExternalMapper mapper;

    @Override
    public Page<Company> getCompaniesWithTransfersLastMonth(Pageable pageRequest) {
        LocalDateTime firstDayOfLastMonth = DateUtils.getFirstDayOfLastMonth();
        LocalDateTime lastDayOfLastMonth = DateUtils.getLastDayOfLastMonth();
        return mapper.toBusinessLayer(companiesRepository.findAllByTransfersCreatedAtBetween(firstDayOfLastMonth,lastDayOfLastMonth,pageRequest));
    }
}