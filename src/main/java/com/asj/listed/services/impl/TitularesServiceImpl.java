package com.asj.listed.services.impl;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.model.dto.TitularDTO;
import com.asj.listed.model.entities.Titular;
import com.asj.listed.model.response.TitularResponse;
import com.asj.listed.repositories.TitularesRepository;
import com.asj.listed.services.intefaces.TitularesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.asj.listed.exceptions.response.ErrorResponse.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class TitularesServiceImpl implements TitularesService {
    public final TitularesRepository titularRepository;

    @Override
    public List<TitularResponse> findAll() {
        log.info("Buscando todos los titulares");
        List<TitularResponse> titulares = titularRepository.findAll().stream()
                .map(TitularResponse::toResponse)
                .collect(Collectors.toList());
        return titulares;
    }

    @Override
    public TitularResponse findById(long id) throws ErrorProcessException {
        Titular titular = titularRepository.findById(id).orElseThrow(() -> new NotFoundException("Titular no encontrada"));
        try {
            return TitularResponse.toResponse(titular);
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public TitularResponse add(TitularDTO titularDTO) throws ErrorProcessException {
        Titular titular = titularRepository.findById(titularDTO.getId()).orElseThrow(() -> new NotFoundException("Titular no encontrada"));
        try {
            return TitularResponse.toResponse(titularRepository.save(titular));
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public TitularResponse update(long id, TitularDTO titularDTO) throws ErrorProcessException {
        Titular titular = titularRepository.findById(id).orElseThrow(() -> new NotFoundException("Titular no encontrada"));
        try {
            if(!StringUtils.isEmpty(titularDTO.getCuit())) titular.setCuit(titularDTO.getCuit());
            if(!StringUtils.isEmpty(titularDTO.getEmail1())) titular.setCuit(titularDTO.getEmail1());
            if(!StringUtils.isEmpty(titularDTO.getEmail2())) titular.setCuit(titularDTO.getEmail2());
            if(!StringUtils.isEmpty(titularDTO.getNombres())) titular.setCuit(titularDTO.getNombres());
            log.info("Actualizando titular con id: {}, datos: {}",id,titular);
            return TitularResponse.toResponse(titularRepository.save(titular));
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public TitularResponse delete(long id) throws ErrorProcessException {
        Titular titularBorrado = titularRepository.findById(id).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        try {
            titularRepository.deleteById(id);
            return TitularResponse.toResponse(titularBorrado);
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
}
