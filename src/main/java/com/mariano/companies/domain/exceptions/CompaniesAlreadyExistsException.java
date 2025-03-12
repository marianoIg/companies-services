package com.mariano.companies.domain.exceptions;

import lombok.Getter;

@Getter
public class CompaniesAlreadyExistsException extends CompaniesException {

    private final CompaniesError errorType;
    private final String value;

    public CompaniesAlreadyExistsException(CompaniesError errorType, String value) {
        super(value);
        this.errorType = errorType;
        this.value = value;
    }
}
