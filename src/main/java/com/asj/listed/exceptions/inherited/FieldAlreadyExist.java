package com.asj.listed.exceptions.inherited;

import com.asj.listed.exceptions.BadRequestException;

public class FieldAlreadyExist extends BadRequestException {
    public FieldAlreadyExist(String campo) {
        super(campo + " already exists in the system");
    }
}
