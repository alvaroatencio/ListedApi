package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.request.HolderRequest;
import com.asj.listed.model.response.GenericResponse;
import com.asj.listed.services.impl.HolderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/titulares")
@Slf4j
public class HolderController {
    public final HolderServiceImpl service;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity findAllHolders() throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(
                true,
                "Titulares buscados exitosamente",
                service.findAll()));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity findHolderById(@PathVariable Integer id) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(
                true,
                "Titulares buscados exitosamente",
                service.findById(id)));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity addHolder(@RequestHeader(name = "Authorization") String token , @RequestBody HolderRequest titularBodyDTO) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(
                true,
                "Titulares creado exitosamente",
                service.add(titularBodyDTO)));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity updateHolder(@PathVariable("id") Integer id, @RequestBody HolderRequest titularBodyDTO) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(
                true,
                "Titulares actualizado exitosamente",
                service.update(id,titularBodyDTO)));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity deleteHolder(@PathVariable("id") int id) throws ErrorProcessException {
        return ResponseEntity.status((HttpStatus.OK)).body(new GenericResponse(
                true,
                "Titular borrado exitosamente",
                service.delete(id)));
    }
}
