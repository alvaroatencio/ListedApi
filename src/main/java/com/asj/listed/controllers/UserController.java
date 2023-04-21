package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.request.UserRequest;
import com.asj.listed.model.response.ResponseUtils;
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
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserServiceImpl usuarioService;
    @GetMapping()
    public ResponseEntity<?> findAllUsers() throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Usuarios buscados exitosamente",
                usuarioService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findUserById(@PathVariable int id) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Usuario buscado exitosamente",
                usuarioService.findById(id));
    }
    @PostMapping()
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequest userRequest) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Usuario creado exitosamente",
                usuarioService.add(userRequest));
    }
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Integer id, @Valid @RequestBody UserRequest userRequest) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Usuario actualizado exitosamente",
                usuarioService.update(id, userRequest));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) throws ErrorProcessException {
        return new ResponseUtils().buildResponse(
                HttpStatus.OK,
                "Usuario eliminado exitosamente",
                usuarioService.delete(id));
    }
}
