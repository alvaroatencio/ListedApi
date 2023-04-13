package com.asj.listed.exceptions;

public class DuplicatedEntityException extends RuntimeException{
    public DuplicatedEntityException(String campo) {
        super(campo + " ya existe en la base de datos");
    }
}
