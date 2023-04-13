package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.dto.TitularDTO;
import com.asj.listed.model.entities.Titular;
import com.asj.listed.mapper.TitularesMapper;
import com.asj.listed.model.response.GeneralResponse;
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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity findAllHolders() throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse(
                true,
                "Titulares buscados exitosamente",
                service.findAll()));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity findHolderById(@PathVariable Integer id) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse(
                true,
                "Titulares buscados exitosamente",
                service.findById(id)));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity addHolder(@RequestHeader(name = "Authorization") String token , @RequestBody TitularDTO titularBodyDTO) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new GeneralResponse(
                true,
                "Titulares creado exitosamente",
                service.add(titularBodyDTO)));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity updateHolder(@PathVariable("id") Integer id, @RequestBody TitularDTO titularBodyDTO) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new GeneralResponse(
                true,
                "Titulares actualizado exitosamente",
                service.update(id,titularBodyDTO)));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity deleteHolder(@PathVariable("id") int id) throws ErrorProcessException {
        return ResponseEntity.status((HttpStatus.OK)).body(new GeneralResponse(
                true,
                "Titular borrado exitosamente",
                service.delete(id)));
    }
}
