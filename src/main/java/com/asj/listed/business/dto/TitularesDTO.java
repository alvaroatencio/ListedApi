package com.asj.listed.business.dto;

import com.asj.listed.business.entities.Usuarios;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitularesDTO {
    private int id;
    @NotBlank
    @NotNull
    @NotEmpty
    private String nombres;
    @NotBlank
    @NotNull
    @NotEmpty
    private String email1;
    private String email2;
    private String cuit;
    @NotBlank
    @NotNull
    @NotEmpty
    private Usuarios id_usuario;
}
