package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.request.HolderRequest;
import com.asj.listed.model.response.ResponseUtils;
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
@RequestMapping("/holders")
@Slf4j
public class HolderController {
    public final HolderServiceImpl service;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> findAllHolders() throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Titulares buscados exitosamente",
                service.findAll());
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findHolderById(@PathVariable Integer id) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Titulares buscados exitosamente",
                service.findById(id));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> addHolder(@RequestBody HolderRequest titularBodyDTO) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Titulares creado exitosamente",
                service.add(titularBodyDTO));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping({"{id}"})
    public ResponseEntity<?> updateHolder(@PathVariable("id") Integer id, @RequestBody HolderRequest titularBodyDTO) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Titulares actualizado exitosamente",
                service.update(id,titularBodyDTO));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteHolder(@PathVariable("id") int id) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Titular borrado exitosamente",
                service.delete(id));
    }
}
