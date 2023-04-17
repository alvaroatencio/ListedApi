package com.asj.listed.model.request;

import com.asj.listed.model.entities.Holder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolderRequest {
    private long id;
    private String cuit;
    private String email1;
    private String email2;
    private String nombres;
    private int id_usuario;
    public static Holder toEntity(HolderRequest holderRequest) {
        return Holder.builder()
                .id(holderRequest.getId())
                .cuit(holderRequest.getCuit())
                .email1(holderRequest.getEmail1())
                .email2(holderRequest.getEmail2())
                .nombres(holderRequest.getNombres())
                .build();
    }
}
