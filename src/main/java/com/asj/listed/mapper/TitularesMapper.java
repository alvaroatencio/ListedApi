package com.asj.listed.mapper;

import com.asj.listed.business.dto.TitularDTO;
import com.asj.listed.business.entities.Titular;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TitularesMapper {
    TitularDTO titularesEntityToTitularesDTO(Titular titular);
    Titular titularesDTOToTitularesEntity(TitularDTO titularDTO);
}
