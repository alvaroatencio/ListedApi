package com.asj.listed.controllers;

import com.asj.listed.model.dto.UsuarioDTO;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.model.response.UsuarioResponse;
import com.asj.listed.services.impl.UsuariosServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/usuarios")
@Slf4j
public class UsuariosController {
    private final UsuariosServiceImpl service;
    public UsuariosController(UsuariosServiceImpl service) {
        this.service = service;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<?>> buscarUsuarios() throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuariosPorId(@Valid @PathVariable int id) throws ErrorProcessException{
        return ResponseEntity.ok(service.findById(id));
    }
    @PostMapping()
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(usuarioDTO));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("id") Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        System.out.println("UsuariosController.actualizarUsuario");
        log.info("Solicitud de actualización de usuario recibida");
        Map<String, Object> response = new HashMap<>();
        if (StringUtils.isEmpty(usuarioDTO.getUsuario())
                || StringUtils.isEmpty(usuarioDTO.getMail())
                || StringUtils.isEmpty(usuarioDTO.getPassword())) {
            response.put("success", Boolean.FALSE);
            response.put("message", "Faltan datos obligatorios para actualizar el usuario");
            log.info("Solicitud de actualización de usuario invalida");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            UsuarioResponse usuarioActualizado = service.update(id, usuarioDTO);
            response.put("success", Boolean.TRUE);
            response.put("message", "Usuario actualizado exitosamente");
            response.put("usuario", usuarioActualizado);
            log.info("Solicitud de actualización de usuario exitosa");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("success", Boolean.FALSE);
            response.put("message", e.getMessage());
            log.info("Solicitud de actualización de usuario rechazada");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", Boolean.FALSE);
            response.put("message", e.getMessage());
            log.info("Error interno al recibir solicitud de actualización de usuario");
            return ResponseEntity.internalServerError().body("Error del servidor, intente mas tarde");
        }
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id) throws ErrorProcessException {
        return ResponseEntity.ok(service.delete(id));
    }
}
