package com.mariano.companies.domain.exceptions;

import lombok.Getter;

@Getter
public class CompaniesNotFoundException extends CompaniesException {
    private final CompaniesError errorType;
    private final String value;

    public CompaniesNotFoundException(CompaniesError errorType, String value) {
        super(String.format(errorType.getDescription(), value));
        this.errorType = errorType;
        this.value = value;
    }
}
