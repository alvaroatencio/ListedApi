package com.asj.listed.model.dto;

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
}
