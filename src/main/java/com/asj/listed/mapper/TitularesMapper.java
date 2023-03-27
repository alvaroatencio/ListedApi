package com.asj.listed.mapper;

import com.asj.listed.business.dto.TitularesDTO;
import com.asj.listed.business.entities.Titulares;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface TitularesMapper {
    TitularesDTO titularesEntityToTitularesDTO(Titulares titular);
    Titulares titularesDTOToTitularesEntity(TitularesDTO titularDTO);
}
