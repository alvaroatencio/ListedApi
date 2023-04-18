package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.request.AccountRequest;
import com.asj.listed.model.response.GenericResponse;
import com.asj.listed.security.services.JwtTokenService;
import com.asj.listed.services.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl service;
    @PreAuthorize("hasRole('ADMIN')or hasRole('USUARIO')")
    @GetMapping()
    public ResponseEntity<GenericResponse> findAllAccounts() throws ErrorProcessException {
        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserResponse usuario = serviceUsuarios.buscarUsuario(authentication.getName());
        if(usuario.getRol().equals(Rol.ADMIN))*/
            return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(
                    true,
                    "Cuentas buscadas exitosamente",
                    service.findAll()));
        /*return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(
                true,
                "Cuentas buscadas exitosamente",
                service.findByUserId(usuario.getId())));*/
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<GenericResponse> findAccountById(@PathVariable int id) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(
                true,
                "Cuentas buscadas exitosamente",
                service.findById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')or hasRole('USUARIO')")
    @PostMapping()
    public ResponseEntity<GenericResponse> addAccount(@RequestBody AccountRequest accountRequest) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(
                true,
                "Cuenta creada exitosamente",
                service.add(accountRequest)));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") Integer id, @RequestBody AccountRequest usuarioDTO) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(
                true,
                "Cuenta actualizada exitosamente",
                service.update(id, usuarioDTO)));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<GenericResponse> deleteAccount(@PathVariable int id) throws ErrorProcessException {
        return ResponseEntity.ok().body(new GenericResponse(
                true,
                "Cuenta actualizada exitosamente",
                service.delete(id)));
    }
}
