package com.asj.listed.model.dto;

import com.asj.listed.model.entities.Usuario;
import com.asj.listed.model.enums.Rol;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
    private Long id;
    private String usuario;
    private String mail;
    private String password;
    private Integer token;
    private Rol rol;

    public static UsuarioRequest toEntity(Usuario usuario){
        return UsuarioRequest.builder()
                .id(usuario.getId())
                .usuario(usuario.getUsuario())
                .mail(usuario.getMail())
                .password(usuario.getPassword())
                .token(usuario.getToken())
                .rol(usuario.getRol())
                .build();
    }
    public static Usuario toEntity(UsuarioRequest usuarioRequest){
        return Usuario.builder()
                .id(usuarioRequest.getId())
                .usuario(usuarioRequest.getUsuario())
                .mail(usuarioRequest.getMail())
                .password(usuarioRequest.getPassword())
                .token(usuarioRequest.getToken())
                .rol(usuarioRequest.getRol())
                .build();
    }
}
