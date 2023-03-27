package com.asj.listed.exceptions;

public class CreateDuplicatedException extends RuntimeException{
    public CreateDuplicatedException(String campo) {
        super("El campo " + campo + " ya existe en la base de datos");
    }
}
