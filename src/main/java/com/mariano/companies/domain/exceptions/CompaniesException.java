package com.mariano.companies.domain.exceptions;

import lombok.Getter;

@Getter
public class CompaniesException extends RuntimeException {

    public CompaniesException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}