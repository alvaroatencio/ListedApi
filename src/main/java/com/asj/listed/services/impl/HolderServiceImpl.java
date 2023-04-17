package com.asj.listed.services.impl;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.model.request.HolderRequest;
import com.asj.listed.model.entities.Holder;
import com.asj.listed.model.response.HolderResponse;
import com.asj.listed.repositories.HolderRepository;
import com.asj.listed.services.intefaces.HolderService;
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
public class HolderServiceImpl implements HolderService {
    public final HolderRepository titularRepository;
    @Override
    public List<HolderResponse> findAll() throws ErrorProcessException {
        try {
            log.info("Searching for all account holders");
            List<HolderResponse> titulares = titularRepository.findAll()
                    .stream()
                    .map(HolderResponse::toResponse)
                    .collect(Collectors.toList());
            log.info("Found account holders: {}", titulares.size());
            return titulares;
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public HolderResponse findById(long id) throws ErrorProcessException {
        Holder holder = titularRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Account holder with id \" + id + \" does not exist"));
        try {
            return HolderResponse.toResponse(holder);
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    //// TODO: 12/4/2023  ARREGLAR EN ESTOS METODOS LAS RELACIONES
    @Override
    public HolderResponse add(HolderRequest holderRequest) throws ErrorProcessException {
        log.info("Account holder creation request received");
        try {
            log.info("Creating account holder: {}", holderRequest.getNombres());
            return HolderResponse.toResponse(titularRepository.save(HolderRequest.toEntity(holderRequest)));
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public HolderResponse update(long id, HolderRequest holderRequest) throws ErrorProcessException {
        log.info("Request for changes on account holder with id: "+ id);
        Holder holder = titularRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Account holder with id " + id + " does not exist"));
        try {
            if(!StringUtils.isEmpty(holderRequest.getCuit()))
                holder.setCuit(holderRequest.getCuit());
            if(!StringUtils.isEmpty(holderRequest.getEmail1()))
                holder.setCuit(holderRequest.getEmail1());
            if(!StringUtils.isEmpty(holderRequest.getEmail2()))
                holder.setCuit(holderRequest.getEmail2());
            if(!StringUtils.isEmpty(holderRequest.getNombres()))
                holder.setCuit(holderRequest.getNombres());
            log.info("Actualizando titular con id: {}, datos: {}",id, holder);
            return HolderResponse.toResponse(titularRepository.save(holder));
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public HolderResponse delete(long id) throws ErrorProcessException {
        Holder holderBorrado = titularRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Account holder with id " + id + " does not exist"));
        try {
            titularRepository.deleteById(id);
            return HolderResponse.toResponse(holderBorrado);
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
}
