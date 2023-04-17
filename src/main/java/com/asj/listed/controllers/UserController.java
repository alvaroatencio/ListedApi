package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.request.UserRequest;
import com.asj.listed.model.response.GenericResponse;
import com.asj.listed.services.impl.UserServiceImpl;
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
public class UserController {
    private final UserServiceImpl usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<?> findAllUsers() throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.OK).body(new GenericResponse(
                true,
                "Usuarios buscados exitosamente",
                usuarioService.findAll()));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable int id) throws ErrorProcessException {
        return ResponseEntity.ok(new GenericResponse(
                true,
                "Usuario buscado exitosamente",
                usuarioService.findById(id)));
    }

    @PostMapping()
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest userRequest) throws ErrorProcessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(
                true,
                "Usuario creado exitosamente",
                usuarioService.add(userRequest)));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @Valid @RequestBody UserRequest userRequest) throws ErrorProcessException {
        return ResponseEntity.ok(new GenericResponse(
                true,
                "Usuario actualizado exitosamente",
                usuarioService.update(id, userRequest)));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) throws ErrorProcessException {
        return ResponseEntity.ok(new GenericResponse(
                true,
                "Usuario eliminado exitosamente",
                usuarioService.delete(id)));
    }
}
