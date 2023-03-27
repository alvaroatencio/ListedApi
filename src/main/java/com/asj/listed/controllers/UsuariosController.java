package com.asj.listed.controllers;

import com.asj.listed.business.dto.UsuariosDTO;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.services.impl.UsuariosServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
@Slf4j
public class UsuariosController {
    private final UsuariosServiceImpl service;
    public UsuariosController(UsuariosServiceImpl service) {
        this.service = service;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UsuariosDTO>> buscarUsuarios() {
        System.out.println("UsuariosController.buscarUsuarios ENTRA");
        log.info("Buscando todos los usuarios");
        List<UsuariosDTO> usuarios = service.listarTodos();
        log.info("Usuarios encontrados: {}", usuarios.size());
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<UsuariosDTO> buscarUsuariosPorId(@PathVariable int id) {
        log.info("Buscando usuario con id: {}", id);
        Optional<UsuariosDTO> usuario = service.buscarPorId(id);
        if (usuario.isPresent()) {
            log.info("Usuario encontrado: {}", usuario.get());
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            log.warn("No se encontró usuario con id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping()
    public ResponseEntity<?> crearUsuario(@RequestBody UsuariosDTO usuarioDTO) {
        log.info("Solicitud de creacion de usuario recibida");
        // Validaciones de nulidad
        if (StringUtils.isEmpty(usuarioDTO.getUsuario())
                || StringUtils.isEmpty(usuarioDTO.getMail())
                || StringUtils.isEmpty(usuarioDTO.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", Boolean.FALSE);
            response.put("message", "Faltan datos obligatorios para crear el usuario");
            return ResponseEntity.badRequest().body(response);
        }
        // Creación de usuario
        UsuariosDTO usuarioCreado = service.crear(usuarioDTO);
        // Respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("success", Boolean.TRUE);
        response.put("message", "Usuario creado exitosamente");
        response.put("usuario", usuarioCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("id") Integer id, @RequestBody UsuariosDTO usuarioDTO) {
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
            UsuariosDTO usuarioActualizado = service.actualizar(id, usuarioDTO);
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
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id) {
        Optional<UsuariosDTO> usuarioEliminado = service.eliminar(id);
        if (usuarioEliminado.isPresent()) {
            return ResponseEntity.ok(usuarioEliminado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
