package com.asj.listed.model.dto;

import com.asj.listed.model.entities.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDTO {
    private Long id;
    private String alias;
    private String banco;
    private String cbu;
    private String nroCuenta;
    private String sucursal;
    private TitularDTO titular;
    private Long usuarioId;

    public static Cuenta toEntity(CuentaDTO cuentaDTO) {
        return Cuenta.builder()
                .id(cuentaDTO.getId())
                .alias(cuentaDTO.getAlias())
                .banco(cuentaDTO.getBanco())
                .cbu(cuentaDTO.getCbu())
                .nroCuenta(cuentaDTO.getNroCuenta())
                .sucursal(cuentaDTO.getSucursal())
                .build();
    }
}
