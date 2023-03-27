package com.asj.listed.controllers;

import com.asj.listed.business.dto.TitularesDTO;
import com.asj.listed.business.entities.Titulares;
import com.asj.listed.mapper.TitularesMapper;
import com.asj.listed.services.impl.TitularesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity buscarTitulares() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity buscarTitularById(@PathVariable Integer id){
        Optional<Titulares> titularBuscado;
        //// TODO: 21/3/2023  no se si este TRY esta bien
        try {
            titularBuscado = service.buscarPorId(id);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(titularBuscado.isPresent())
            return ResponseEntity.status(HttpStatus.OK)
            .body(mapper.titularesEntityToTitularesDTO(titularBuscado.get()));
        Map<String,Object> response = new HashMap<>();
        response.put("sucess", Boolean.FALSE);
        response.put("message","No se encontro el titular");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @PostMapping
    public ResponseEntity crearTitular(@RequestBody TitularesDTO titularBodyDTO){
        Titulares titularCreado = service.crear(mapper.titularesDTOToTitularesEntity(titularBodyDTO));
        log.info("CREATE titular : " + titularCreado.getNombres());
        return ResponseEntity.status(HttpStatus.CREATED).body(titularCreado);
    }
    @PutMapping
    public ResponseEntity actualizarTitular(@PathVariable("id") Integer id,@RequestBody TitularesDTO titularBodyDTO){
        Titulares titularBody = mapper.titularesDTOToTitularesEntity(titularBodyDTO);
        TitularesDTO titularResponseDTO = mapper.titularesEntityToTitularesDTO(service.actualizar(id,titularBody));
        log.info("UPDATE titular con id: "+ id +"\nDatos previos: "+titularBody.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(titularResponseDTO);
    }
    @DeleteMapping("{id}")
    public ResponseEntity borrarTitular(@PathVariable("id") int id) {
        Optional<Titulares> titularBorrado = service.eliminar(id);
        Map<String,Object> response = new HashMap<>();
        response.put("sucess", Boolean.TRUE);
        response.put("message",titularBorrado);
        log.info("DELETE titular con id: "+id+"\nDatos previos: "+titularBorrado.get().toString());
        return ResponseEntity.status((HttpStatus.OK)).body(response);
    }
}
