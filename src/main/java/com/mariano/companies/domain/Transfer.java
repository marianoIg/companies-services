package com.mariano.companies.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Transfer {
    private UUID id;
    private Float amount;
    private LocalDateTime createdAt;
    private String creditAccount;
    private String debitAccount;
    private Long companyId;
}
