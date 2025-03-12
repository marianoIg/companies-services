package com.mariano.companies.domain.exceptions;

public enum CompaniesError implements CompaniesErrorType {

    COM_ERR_NEG_001("COM_ERR_NEG_001", "No se encontró empresa con id: %s"),
    COM_ERR_NEG_002("COM_ERR_NEG_002", "Ya existe una empresa con nombre: %s y cuit: %s"),
    COM_ERR_NEG_003("COM_ERR_NEG_003", "Campo/s inválido/s"),
    COM_ERR_NEG_004("COM_ERR_NEG_004", "%s"),
    COM_ERR_NEG_005("COM_ERR_NEG_005", "ID inválido, el ID de una empresa debe ser mayor o igual a 1."),

    COM_ERR_GEN_001("COM_ERR_GEN_001", "Excepción no controlada, intente nuevamente"),

    COM_ERR_SEC_001("COM_ERR_SEC_001", "Esta acción requiere ingresar un usuario y contraseña válidos"),
    COM_ERR_SEC_002("COM_ERR_SEC_002", "Esta acción debe realizarse con un usuario y contraseña autorizados");

    private final String code;
    private final String description;

    CompaniesError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
}