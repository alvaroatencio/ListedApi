package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.dto.UsuarioRequest;
import com.asj.listed.model.response.GeneralResponse;
import com.asj.listed.services.impl.UsuariosServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
@Slf4j
public class UsuariosController {
    private final UsuariosServiceImpl usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> findAllUsers() throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GeneralResponse(
                true,
                "Usuario creado exitosamente",
                usuarioService.findAll()));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable int id) throws ErrorProcessException {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@Valid @RequestBody UsuarioRequest usuarioRequest) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new GeneralResponse(
                true,
                "Usuario creado exitosamente",
                usuarioService.add(usuarioRequest)));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @Valid @RequestBody UsuarioRequest usuarioRequest) throws ErrorProcessException {
        return ResponseEntity.ok(new GeneralResponse(
                true,
                "Usuario actualizado exitosamente",
                usuarioService.update(id, usuarioRequest)));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) throws ErrorProcessException {
        return ResponseEntity.ok(usuarioService.delete(id));
    }
}
