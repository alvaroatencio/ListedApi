package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.dto.CuentaDTO;
import com.asj.listed.model.enums.Rol;
import com.asj.listed.model.response.CuentasResponse;
import com.asj.listed.model.response.GeneralResponse;
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

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@Slf4j
@RequiredArgsConstructor
public class CuentasController {
    private final CuentasServiceImpl service;
    private final UsuariosServiceImpl serviceUsuarios;
    @Autowired
    JwtService tokenService;

    @PreAuthorize("hasRole('ADMIN')or hasRole('USUARIO')")
    @GetMapping()
    public ResponseEntity<?> findAllAccounts() throws ErrorProcessException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioResponse usuario = serviceUsuarios.buscarUsuario(authentication.getName());
        List<CuentasResponse> cuentas;
        if (usuario.getRol().equals(Rol.ADMIN)) {
            log.info("Buscando las cuentas del admin {}", usuario.getId());
            return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse(
                    true,
                    "Cuentas buscadas exitosamente",
                    service.findAll()));
        } else if (usuario.getRol().equals(Rol.USUARIO)) {
            log.info("Buscando las cuentas del usuario {}", usuario.getId());
            cuentas = service.buscarPorId_usuario(usuario.getId());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado para la solicitud");
        }
        log.info("Cuentas encontradas: {}", cuentas.size());
        return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse(
                true,
                "Cuentas buscadas exitosamente",
                cuentas));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<?> findAccountById(@PathVariable int id) throws ErrorProcessException {
        log.info("Buscando cuenta con id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')or hasRole('USUARIO')")
    @PostMapping()
    public ResponseEntity<?> addAccount(@RequestBody CuentaDTO cuentaDTO) throws ErrorProcessException {
        log.info("Solicitud de creacion de cuenta recibida");
        return ResponseEntity.status(HttpStatus.CREATED).body(new GeneralResponse(
                true,
                "Cuenta creada exitosamente",
                service.add(cuentaDTO)));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") Integer id, @RequestBody CuentaDTO usuarioDTO) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse(
                true,
                "Cuenta actualizada exitosamente",
                service.update(id, usuarioDTO)));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable int id) throws ErrorProcessException {
        CuentasResponse usuarioEliminado = service.delete(id);
        return ResponseEntity.ok().body(usuarioEliminado);
    }
}
