package com.mariano.companies.api.v1;

import com.mariano.companies.api.v1.dto.CompanyTransfersV1;
import com.mariano.companies.api.v1.mapper.CompaniesTransfersMapper;
import com.mariano.companies.domain.service.CompaniesTransfersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies/transfers")
@Tag(name = "Transfers", description = "Consulta de movimientos de empresas")
public class CompaniesTransfersController {

    private final CompaniesTransfersService companiesTransfersService;
    private final CompaniesTransfersMapper companiesTransfersMapper;

    @GetMapping("/last-month")
    @Operation(
            summary = "Obtener empresas con transferencias en el último mes",
            description = "Retorna una lista de todas las empresas con transferencias en el ultimo mes.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
            }
    )
    public ResponseEntity<Page<CompanyTransfersV1>> getCompaniesWithTransfersLastMonth(@PageableDefault(size = 5, page = 0) Pageable pageable) {
        var companies = companiesTransfersService.getCompaniesWithTransfersLastMonth(pageable);
        var response = companies.map(companiesTransfersMapper::toOuterLayer);
        return ResponseEntity.ok(response);
    }
}