package com.asj.listed.security.controllers;

import com.asj.listed.security.dtos.JwtResponseDTO;
import com.asj.listed.security.services.JwtService;
import com.asj.listed.security.dtos.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            Authentication authentication = daoAuthenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsuario(), loginRequestDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.crearToken(authentication);
            return ResponseEntity.ok(new JwtResponseDTO(token));
        } catch (AuthenticationException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", Boolean.FALSE);
            response.put("message", "Credenciales invalidas, intente nuevamente");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}