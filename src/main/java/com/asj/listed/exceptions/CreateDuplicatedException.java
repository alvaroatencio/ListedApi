package com.asj.listed.exceptions;

public class CreateDuplicatedException extends RuntimeException{
    public CreateDuplicatedException(String campo) {
        super(campo + " ya existe en la base de datos");
    }
}
