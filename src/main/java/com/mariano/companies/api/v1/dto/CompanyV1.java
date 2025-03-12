package com.mariano.companies.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Datos requeridos para crear o actualizar una compania")
public class CompanyV1 {

    @Schema(description = "ID único de la empresa", example = "1")
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^(20|23|27|30|33)-\\d{8}-\\d{1}$", message = "CUIT debe tener el formato correcto (XX-XXXXXXXX-X) y los primeros dos dígitos deben ser uno de los siguientes: 20, 23, 27, 30, 33.")
    @Schema(description = "CUIT de la empresa", example = "30-12345678-9")
    private String cuit;

    @NotEmpty
    @Schema(description = "Razón social de la empresa", example = "Ejemplo S.A.")
    private String businessName;

    @Schema(description = "Fecha de creación de la empresa", example = "2025-01-01T10:00:00")
    private LocalDateTime createdAt;
}