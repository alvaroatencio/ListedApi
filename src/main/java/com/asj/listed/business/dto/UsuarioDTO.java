package com.asj.listed.business.dto;

import com.asj.listed.business.entities.Cuenta;
import com.asj.listed.business.enums.Rol;
import jakarta.persistence.OneToMany;
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
