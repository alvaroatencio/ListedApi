package com.asj.listed.model.response;

import com.asj.listed.model.entities.Holder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HolderResponse {
    private long id;
    private String cuit;
    private String email1;
    private String email2;
    private String nombres;
    private long id_usuario;
    public static HolderResponse toResponse(Holder holder){
        return HolderResponse.builder()
                .id(holder.getId())
                .cuit(holder.getCuit())
                .email1(holder.getEmail1())
                .email2(holder.getEmail2())
                .nombres(holder.getNombres())
                .id_usuario(holder.getUser().getId())
                .build();
    }
}
