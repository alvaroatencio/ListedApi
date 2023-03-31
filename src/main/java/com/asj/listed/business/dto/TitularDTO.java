package com.asj.listed.business.dto;

import com.asj.listed.business.entities.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitularDTO {
    private int id;
    private String cuit;
    private String email1;
    private String email2;
    private String nombres;
    private int id_usuario;
}
