package com.asj.listed.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Class<?> clazz, String id) {
        super("No se encontró " + clazz.getSimpleName() + " con id " + id );
    }
    public NotFoundException(String mensaje) {
        super(mensaje);
    }
}
