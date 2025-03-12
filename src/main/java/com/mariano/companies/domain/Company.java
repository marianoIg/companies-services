package com.mariano.companies.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Company {
    private Long id;
    private String businessName;
    private String cuit;
    private LocalDateTime createdAt;
    private List<Transfer> transfers;
}
