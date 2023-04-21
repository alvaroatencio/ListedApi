package com.asj.listed.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestRoleController {
    @GetMapping("/usuario")
    public ResponseEntity<?> testUsuario() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("autorizado");
    }
    @GetMapping("/admin")
    public ResponseEntity<?> getAdmin() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("autorizado");
    }
    @GetMapping("/bloqueado")
    public ResponseEntity<?> getBloqueado() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("autorizado");
    }
}
