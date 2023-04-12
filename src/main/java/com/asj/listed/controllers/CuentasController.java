package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.dto.CuentaDTO;
import com.asj.listed.model.dto.UsuarioDTO;
import com.asj.listed.model.enums.Rol;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.mapper.CuentasMapper;
import com.asj.listed.model.response.CuentasResponse;
import com.asj.listed.model.response.UsuarioResponse;
import com.asj.listed.security.services.JwtService;
import com.asj.listed.services.impl.CuentasServiceImpl;
import com.asj.listed.services.impl.UsuariosServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cuentas")
@Slf4j
@RequiredArgsConstructor
public class CuentasController {
    private final CuentasServiceImpl service;
    private final UsuariosServiceImpl serviceUsuarios;
    private final CuentasMapper mapper;
    @Autowired
    JwtService tokenService;

    @PreAuthorize("hasRole('ADMIN')or hasRole('USUARIO')")
    @GetMapping()
    public ResponseEntity<?> buscarCuentas() throws ErrorProcessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioResponse usuario = serviceUsuarios.buscarUsuario(authentication.getName());
        log.info("Buscando las cuentas del usuario {}", usuario.getId());
        List<CuentasResponse> cuentas;
        if (usuario.getRol().equals(Rol.ADMIN)) {
             cuentas = service.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(cuentas);
        } else if (usuario.getRol().equals(Rol.USUARIO)) {
             cuentas = service.buscarPorId_usuario(usuario.getId());
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado para la solicitud");
        }
        log.info("Cuentas encontradas: {}", cuentas.size());
        return ResponseEntity.status(HttpStatus.OK).body(cuentas);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<?> buscarCuentasPorId(@PathVariable int id) throws ErrorProcessException {
        log.info("Buscando cuenta con id: {}", id);
        return  ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }
    @PreAuthorize("hasRole('ADMIN')or hasRole('USUARIO')")
    @PostMapping()
    public ResponseEntity<?> crearCuenta(@RequestHeader(name = "Authorization") String token , @RequestBody CuentaDTO cuentaDTO) throws ErrorProcessException {
        log.info("Solicitud de creacion de cuenta recibida");
        // Validaciones de nulidad
        //// TODO: 27/3/2023  sacar esto este false
        if (false) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", Boolean.FALSE);
            response.put("message", "Faltan datos obligatorios para crear la cuenta");
            return ResponseEntity.badRequest().body(response);
        }
        //long idUsuario=serviceUsuarios.buscarUsuario(tokenService.getUserNameFromToken(token)).getId();
        cuentaDTO.setUsuarioId(22L);
        System.out.println(cuentaDTO);
        // Creación de cuenta
        CuentasResponse cuentaCreada = service.add(cuentaDTO);
        System.out.println("CuentasController.crearCuenta"+cuentaCreada);
        // Respuesta
        Map<String, Object> response = new HashMap<>();
        response.put("success", Boolean.TRUE);
        response.put("message", "Cuenta creada exitosamente");
        response.put("cuenta", cuentaCreada);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("id") Integer id, @RequestBody CuentaDTO usuarioDTO) {
        log.info("Solicitud de actualización de cuenta recibida");
        Map<String, Object> response = new HashMap<>();
        try {
            CuentasResponse cuentaActualizada = service.update(id, usuarioDTO);
            response.put("success", Boolean.TRUE);
            response.put("message", "Cuenta actualizada exitosamente");
            response.put("usuario", cuentaActualizada);
            log.info("Solicitud de actualización de cuenta exitosa");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("success", Boolean.FALSE);
            response.put("message", e.getMessage());
            log.info("Solicitud de actualización de cuenta rechazada");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("success", Boolean.FALSE);
            response.put("message", e.getMessage());
            log.info("Error interno al recibir solicitud de actualización de cuenta");
            return ResponseEntity.internalServerError().body("Error del servidor, intente mas tarde");
        }
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable int id) throws ErrorProcessException {
        CuentasResponse usuarioEliminado = service.delete(id);
        return ResponseEntity.ok().body(usuarioEliminado);
    }
}
