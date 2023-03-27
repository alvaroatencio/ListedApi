package com.asj.listed.business.dto;

import com.asj.listed.business.entities.Titulares;
import com.asj.listed.business.entities.Usuarios;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentasDTO {
    private int codigo;
    @NotBlank
    @NotNull
    @NotEmpty
    private Usuarios id_usuario;
    @NotBlank
    @NotNull
    @NotEmpty
    private Titulares id_titular;
    private String cbu;
    private String nro_cuenta;
    private  String banco;
    private String alias;
    private String sucursal;

}
