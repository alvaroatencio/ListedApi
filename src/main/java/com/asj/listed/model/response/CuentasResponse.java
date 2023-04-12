package com.asj.listed.model.response;

import com.asj.listed.model.dto.TitularDTO;
import com.asj.listed.model.entities.Cuenta;
import com.asj.listed.model.entities.Titular;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentasResponse {
    private Long id;
    private String alias;
    private String banco;
    private String cbu;
    private String nroCuenta;
    private String sucursal;
    private Titular titular;
    private Long usuarioId;

    public static  CuentasResponse toResponse(Cuenta cuenta){
        return CuentasResponse.builder()
                .id(cuenta.getId())
                .alias(cuenta.getAlias())
                .banco(cuenta.getBanco())
                .cbu(cuenta.getCbu())
                .nroCuenta(cuenta.getNroCuenta())
                .sucursal(cuenta.getSucursal())
                .titular(cuenta.getTitular())
                .usuarioId(cuenta.getUsuario().getId())
                .build();
    }
}
