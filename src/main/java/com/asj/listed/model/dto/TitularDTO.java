package com.asj.listed.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitularDTO {
    private long id;
    private String cuit;
    private String email1;
    private String email2;
    private String nombres;
    private int id_usuario;
}
