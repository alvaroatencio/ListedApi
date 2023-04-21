package com.asj.listed.security.controllers;

import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.model.response.ResponseUtils;
import com.asj.listed.security.dtos.LoginRequestDTO;
import com.asj.listed.security.services.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthController.AUTH)
@Slf4j
public class AuthController {
    public static final String AUTH = "/auth";
    public static final String LOGIN = "/login";
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtTokenService jwtTokenService;
    @PostMapping(AuthController.LOGIN)
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) throws AuthenticationException  {
            System.out.println("AuthController.login 1");
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsuario(), loginRequestDTO.getPassword()));
            System.out.println("AuthController.login 2");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("AuthController.login 3");
            return new ResponseUtils().buildResponse(
                    HttpStatus.OK,
                    "Valid credentials",
                    jwtTokenService.generateToken(authentication));
    }
}