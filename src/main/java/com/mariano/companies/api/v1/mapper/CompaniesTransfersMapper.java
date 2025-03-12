package com.mariano.companies.api.v1.mapper;

import com.mariano.companies.api.v1.dto.CompanyTransfersV1;
import com.mariano.companies.domain.Company;
import com.mariano.companies.domain.common.LayerMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompaniesTransfersMapper extends LayerMapper<Company, CompanyTransfersV1> {

}
