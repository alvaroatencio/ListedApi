package com.asj.listed.model.request;

import com.asj.listed.model.entities.User;
import com.asj.listed.model.enums.Rol;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;
    private String usuario;
    private String mail;
    private String password;
    private Integer token;
    private Rol rol;
    public static User toEntity(UserRequest userRequest){
        return User.builder()
                .id(userRequest.getId())
                .usuario(userRequest.getUsuario())
                .mail(userRequest.getMail())
                .password(userRequest.getPassword())
                .token(userRequest.getToken())
                .rol(userRequest.getRol())
                .build();
    }
}