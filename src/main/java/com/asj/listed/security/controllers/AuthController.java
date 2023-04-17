package com.asj.listed.security.controllers;

import com.asj.listed.model.response.GenericResponse;
import com.asj.listed.security.dtos.LoginRequestDTO;
import com.asj.listed.security.services.JwtTokenService;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;
    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        Authentication authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsuario(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(new GenericResponse(
                true,
                "Valid credentials",
                jwtTokenService.createToken(authentication)));
    }
}