package com.asj.listed.model.response;

import com.asj.listed.model.entities.Cuenta;
import com.asj.listed.model.entities.Usuario;
import com.asj.listed.model.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {
    private Long id;
    private String usuario;
    private String mail;
    private String password;
    private Integer token;
    private Rol rol;

    public static  UsuarioResponse toResponse(Usuario usuario){
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .usuario(usuario.getUsuario())
                .mail(usuario.getMail())
                .password(usuario.getPassword())
                .token(usuario.getToken())
                .rol(usuario.getRol())
                .build();
    }
}
