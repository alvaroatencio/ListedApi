package com.asj.listed.model.response;

import com.asj.listed.model.entities.Titular;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TitularResponse {
    private long id;
    private String cuit;
    private String email1;
    private String email2;
    private String nombres;
    private long id_usuario;

    public static TitularResponse toResponse(Titular titular){
        return TitularResponse.builder()
                .id(titular.getId())
                .cuit(titular.getCuit())
                .email1(titular.getEmail1())
                .email2(titular.getEmail2())
                .nombres(titular.getNombres())
                .id_usuario(titular.getUsuario().getId())
                .build();
    }
}
