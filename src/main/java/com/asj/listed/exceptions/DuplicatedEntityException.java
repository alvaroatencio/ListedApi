package com.asj.listed.exceptions;

public class DuplicatedEntityException extends RuntimeException{
    public DuplicatedEntityException(String campo) {
        super(campo + " already exists in the system");
    }
}
