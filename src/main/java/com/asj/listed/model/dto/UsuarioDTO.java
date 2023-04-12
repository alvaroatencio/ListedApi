package com.asj.listed.model.dto;

import com.asj.listed.model.enums.Rol;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String usuario;
    private String mail;
    private String password;
    private Integer token;
    private Rol rol;
}
