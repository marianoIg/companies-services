package com.mariano.companies.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Detalles de una empresa con sus transferencias")
public class CompanyTransfersV1 {

    @Schema(description = "ID único de la empresa", example = "1")
    private Long id;

    @NotEmpty
    @Schema(description = "CUIT de la empresa", example = "20-12345678-9")
    private String cuit;

    @NotEmpty
    @Schema(description = "Razón social de la empresa", example = "NASA")
    private String businessName;

    @NotNull
    @Schema(description = "Fecha de creación de la empresa", example = "2025-01-01T10:00:00")
    private LocalDateTime createdAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Lista de transferencias realizadas por la empresa",
            example = "[{\"id\": \"b3f3f76a-8c4b-4b8f-8ac9-f5f03920c6e9\", \"amount\": 1000, \"fromAccount\": \"123456\", \"toAccount\": \"789101\"}]")
    private List<TransferV1> transfers;
}
