package com.mariano.companies.api.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Detalles de una transferencia realizada por una empresa")
public class TransferV1 {

    @NotNull
    @Schema(description = "ID único de la transferencia", example = "b3f3f76a-8c4b-4b8f-8ac9-f5f03920c6e9")
    private UUID id;

    @NotNull
    @Schema(description = "Monto de la transferencia", example = "1000")
    private Double amount;

    @NotNull
    @Schema(description = "Cuenta desde la que se realiza la transferencia", example = "123456")
    private String debitAccount;

    @NotNull
    @Schema(description = "Cuenta hacia la que se realiza la transferencia", example = "789101")
    private String creditAccount;

    @NotNull
    @Schema(description = "Fecha y hora en que se realizó la transferencia", example = "2025-03-12T15:30:00")
    private LocalDateTime createdAt;
}
