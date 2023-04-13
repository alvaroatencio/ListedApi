package com.asj.listed.model.dto;

import com.asj.listed.model.entities.Titular;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitularDTO {
    private long id;
    private String cuit;
    private String email1;
    private String email2;
    private String nombres;
    private int id_usuario;
    public static Titular toEntity(TitularDTO titularDTO) {
        return Titular.builder()
                .id(titularDTO.getId())
                .cuit(titularDTO.getCuit())
                .email1(titularDTO.getEmail1())
                .email2(titularDTO.getEmail2())
                .nombres(titularDTO.getNombres())
                .build();
    }
}
