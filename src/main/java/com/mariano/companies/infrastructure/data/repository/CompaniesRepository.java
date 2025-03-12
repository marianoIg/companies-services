package com.mariano.companies.infrastructure.data.repository;

import com.mariano.companies.infrastructure.data.entity.CompanyEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface CompaniesRepository extends JpaRepository <CompanyEntity,Long> {
    boolean existsByCuit(String cuit);
    Page<CompanyEntity> findAllCompaniesByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
    Page<CompanyEntity> findAllByTransfersCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}