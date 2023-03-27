package com.asj.listed.exceptions;

public class MissingParamException extends RuntimeException {
    public MissingParamException(String campo) {
        super("El campo " + campo + " no puede estar vacío");
    }
}
