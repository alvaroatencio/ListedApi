package com.asj.listed.model.response;

import com.asj.listed.model.entities.User;
import com.asj.listed.model.enums.Rol;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String usuario;
    private String mail;
    private Integer token;
    private Rol rol;
    public static UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .usuario(user.getUsuario())
                .mail(user.getMail())
                .token(user.getToken())
                .rol(user.getRol())
                .build();
    }
}
