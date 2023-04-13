package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.dto.TitularDTO;
import com.asj.listed.model.entities.Titular;
import com.asj.listed.mapper.TitularesMapper;
import com.asj.listed.model.response.TitularResponse;
import com.asj.listed.services.impl.TitularesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/titulares")
@Slf4j
public class TitularesController {
    public final TitularesServiceImpl service;
    public final TitularesMapper mapper;

    public TitularesController(TitularesServiceImpl service, @Qualifier("titularesMapperImpl")TitularesMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @GetMapping()
    public ResponseEntity buscarTitulares() throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity buscarTitularById(@PathVariable Integer id){
        Titular titularBuscado;
        //// TODO: 21/3/2023  no se si este TRY esta bien
        return ResponseEntity.status(HttpStatus.OK).body("rehacer");
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity crearTitular(@RequestHeader(name = "Authorization") String token ,@RequestBody TitularDTO titularBodyDTO) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(titularBodyDTO));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity actualizarTitular(@PathVariable("id") Integer id,@RequestBody TitularDTO titularBodyDTO) throws ErrorProcessException {
        TitularResponse titularResponseDTO = service.update(id,titularBodyDTO);
        log.info("UPDATE titular con id: "+ id +"\nDatos previos: "+titularBodyDTO.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(titularResponseDTO);
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity borrarTitular(@PathVariable("id") int id) throws ErrorProcessException {
        TitularResponse titularBorrado = service.delete(id);
        Map<String,Object> response = new HashMap<>();
        response.put("sucess", Boolean.TRUE);
        response.put("message",titularBorrado);
        log.info("DELETE titular con id: "+id+"\nDatos previos: "+titularBorrado.toString());
        return ResponseEntity.status((HttpStatus.OK)).body(response);
    }
}
