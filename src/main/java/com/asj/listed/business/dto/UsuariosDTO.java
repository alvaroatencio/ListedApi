package com.asj.listed.business.dto;

import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosDTO{
    private int id;
    @NotBlank
    private String usuario;
    @NotBlank
    private String mail;
    @NotBlank
    private String password;
}
