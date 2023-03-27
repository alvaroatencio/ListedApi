package com.asj.listed.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class TestAuthController {
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACTIVO')")
    @GetMapping("/resource")
    public ResponseEntity<String> getResource() {
        return ResponseEntity.ok("Este es un recurso protegido!");
    }
}
