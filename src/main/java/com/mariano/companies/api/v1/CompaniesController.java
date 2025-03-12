package com.mariano.companies.api.v1;

import com.mariano.companies.api.common.CrudController;
import com.mariano.companies.api.v1.mapper.CompaniesMapper;
import com.mariano.companies.api.v1.dto.CompanyV1;
import com.mariano.companies.domain.service.CompaniesService;
import com.mariano.companies.domain.Company;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Validated
@RequestMapping("/api/companies")
@Tag(name = "Companies", description = "CRUD de empresas")
public class CompaniesController extends CrudController<CompanyV1, Company> {

    private final CompaniesMapper companiesMapper;
    private final CompaniesService companiesService;

    public CompaniesController(CompaniesMapper companiesMapper, CompaniesService companiesService) {
        super(companiesMapper, companiesService);
        this.companiesMapper = companiesMapper;
        this.companiesService = companiesService;
    }

    @Operation(
            summary = "Registrar una nueva empresa",
            description = "Crea y guarda una nueva empresa en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Empresa creada exitosamente",
                            content = @Content(schema = @Schema(implementation = CompanyV1.class))),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
                    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
            }
    )
    @PostMapping
    public ResponseEntity<CompanyV1> create(@RequestBody @Valid CompanyV1 companyV1) {
        ResponseEntity<CompanyV1> response = super.create(companyV1);
        URI location = URI.create("/api/companies/" + response.getBody().getId());
        return ResponseEntity.created(location).body(response.getBody());
    }

    @Operation(
            summary = "Actualizar una empresa",
            description = "Modifica los datos de una empresa existente.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa actualizada correctamente"),
                    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
                    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CompanyV1> update(
            @Parameter(description = "ID de la empresa") @PathVariable Long id,
            @RequestBody @Valid CompanyV1 companyV1) {
        companyV1.setId(id);
        return super.update( companyV1);
    }

    @Operation(
            summary = "Eliminar una empresa",
            description = "Elimina una empresa por su ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Empresa eliminada exitosamente"),
                    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "ID de la empresa") @PathVariable Long id) {
        return super.delete(id);
    }

    @Operation(
            summary = "Obtener una empresa por ID",
            description = "Retorna los detalles de una empresa específica.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empresa encontrada",
                            content = @Content(schema = @Schema(implementation = CompanyV1.class))),
                    @ApiResponse(responseCode = "404", description = "Empresa no encontrada")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CompanyV1> getById(
            @Parameter(description = "ID de la empresa", example = "1") @PathVariable
            @Min(value = 1, message = "El ID debe ser un número positivo mayor a 0") Long id) {
        return super.getById(id);
    }

    @Operation(
            summary = "Obtener todas las empresas registradas en el ultimo mes.",
            description = "Retorna una lista de todas las empresas registradas en el ultimo mes.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
            }
    )
    @GetMapping("/created-last-month")
    public ResponseEntity<Page<CompanyV1>> getAllCreatedLastMonth(
            @PageableDefault(size = 5, page = 0) Pageable pageable) {
        var companies = companiesService.getCompaniesCreatedLastMonth(pageable);
        var response = companies.map(companiesMapper::toOuterLayer);
        return ResponseEntity.ok(response);
    }
}