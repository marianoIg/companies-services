package com.mariano.companies;

import com.mariano.companies.api.v1.dto.CompanyTransfersV1;
import com.mariano.companies.api.v1.dto.CompanyV1;
import com.mariano.companies.api.v1.dto.TransferV1;
import com.mariano.companies.domain.Company;
import com.mariano.companies.domain.Transfer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Fixture {
    public static final String RESET_DATABASE_SCRIPT = "classpath:/db/reset.sql";

    public static final String INIT_LAST_MONTH_DATABASE_SCRIPT = "classpath:/db/insert_companies_last_month.sql";
    public static final String BUSSINES_NAME = "Enterprise S.R.L.";
    public static final String CUIT = "30-99999999-9";
    public static final String INIT_LAST_MONTH_TRANFERS_DATABASE_SCRIPT = "classpath:/db/insert_transfers_last_month.sql";
    public static final String RESET_LAST_MONTH_TRANFERS_DATABASE_SCRIPT = "classpath:/db/reset_transfers_last_month.sql";

    public static Company getCompany() {
        return new Company(
                1L,
                BUSSINES_NAME,
                CUIT,
                LocalDateTime.of(2022,2,15,12,30),
                null
        );
    }

    public static CompanyV1 getCompanyV1() {
        var companyV1 = new CompanyV1();
        companyV1.setCuit(CUIT);
        companyV1.setBusinessName(BUSSINES_NAME);
        return companyV1;
    }

    public static CompanyV1 getCompanyV1_not_valid() {
        var companyV1 = new CompanyV1();
        companyV1.setCuit(CUIT);
        companyV1.setBusinessName(BUSSINES_NAME);
        return companyV1;
    }

    public static Company getCompanyWithTransfers() {
        Transfer transfer = new Transfer(
                UUID.randomUUID(),
                30000f,
                LocalDateTime.now().minusDays(67),
                "",
                "",
                1L
        );

        List<Transfer> transfers = new ArrayList<>();
        transfers.add(transfer);

        return new Company(
                1L,
                BUSSINES_NAME,
                CUIT,
                LocalDateTime.of(2022, 2, 15, 12, 30),
                transfers
        );
    }

    public static CompanyTransfersV1 getCompanyTransfersV1() {
        TransferV1 transfer = TransferV1.builder()
                .id(UUID.randomUUID())
                .amount(30000.0)
                .debitAccount("123456")
                .creditAccount("789101")
                .build();

        List<TransferV1> transfers = new ArrayList<>();
        transfers.add(transfer);

        return CompanyTransfersV1.builder()
                .id(1L)
                .businessName(BUSSINES_NAME)
                .cuit(CUIT)
                .createdAt(LocalDateTime.of(2022, 2, 15, 12, 30))
                .transfers(transfers)
                .build();
    }
}