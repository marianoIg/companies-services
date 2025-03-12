package com.mariano.companies.api.v1.mapper;

import com.mariano.companies.api.v1.dto.CompanyV1;
import com.mariano.companies.domain.common.LayerMapper;
import com.mariano.companies.domain.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompaniesMapper extends LayerMapper<Company, CompanyV1> {

}