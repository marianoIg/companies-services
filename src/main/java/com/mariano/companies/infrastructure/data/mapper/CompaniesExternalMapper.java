package com.mariano.companies.infrastructure.data.mapper;

import com.mariano.companies.domain.common.LayerMapper;
import com.mariano.companies.domain.Company;
import com.mariano.companies.infrastructure.data.entity.CompanyEntity;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CompaniesExternalMapper extends LayerMapper<Company, CompanyEntity> {

}
