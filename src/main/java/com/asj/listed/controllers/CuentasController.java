package com.asj.listed.controllers;

import com.asj.listed.business.dto.CuentasDTO;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.mapper.CuentasMapper;
import com.asj.listed.services.impl.CuentasServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/cuentas")
@Slf4j
public class CuentasController {
    private final CuentasServiceImpl service;
    private final CuentasMapper mapper;

    public CuentasController(CuentasServiceImpl service, @Qualifier("cuentasMapperImpl")CuentasMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<CuentasDTO>> buscarUsuarios() {
        System.out.println("UsuariosController.buscarUsuarios ENTRA");
        log.info("Buscando todos los usuarios");
        List<CuentasDTO> usuarios = service.listarTodos();
        log.info("Usuarios encontrados: {}", usuarios.size());
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<CuentasDTO> buscarUsuariosPorId(@PathVariable int id) {
        log.info("Buscando usuario con id: {}", id);
        Optional<CuentasDTO> usuario = service.buscarPorId(id);
        if (usuario.isPresent()) {
            log.info("Usuario encontrado: {}", usuario.get());
            return ResponseEntity.status(HttpStatus.OK).body(usuario.get());
        } else {
            log.warn("No se encontró usuario con id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PostMapping()
    public ResponseEntity<?> crearCuenta(@RequestBody CuentasDTO cuentaDTO) {
        log.info("Solicitud de creacion de usuario recibida");
        // Validaciones de nulidad
        //// TODO: 27/3/2023  sacar esto este false
        if (false) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", Boolean.FALSE);
            response.put("message", "Faltan datos obligatorios para crear la cuenta");
            return ResponseEntity.badRequest().body(response);
        }
        // Creación de usuario
        CuentasDTO cuentaCreada = service.crear(cuentaDTO);
        // Respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("success", Boolean.TRUE);
        response.put("message", "Cuenta creado exitosamente");
        response.put("cuenta", cuentaCreada);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("id") Integer id, @RequestBody CuentasDTO usuarioDTO) {
        log.info("Solicitud de actualización de usuario recibida");
        Map<String, Object> response = new HashMap<>();
        // TODO: 27/3/2023  sacar este false
        if (false) {
            response.put("success", Boolean.FALSE);
            response.put("message", "Faltan datos obligatorios para actualizar el usuario");
            log.info("Solicitud de actualización de usuario invalida");
            return ResponseEntity.badRequest().body(response);
        }
        try {
            CuentasDTO usuarioActualizado = service.actualizar(id, usuarioDTO);
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
        Optional<CuentasDTO> usuarioEliminado = service.eliminar(id);
        if (usuarioEliminado.isPresent()) {
            return ResponseEntity.ok(usuarioEliminado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
