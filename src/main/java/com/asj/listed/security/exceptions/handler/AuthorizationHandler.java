package com.asj.listed.security.exceptions.handler;

import com.asj.listed.exceptions.BadRequestException;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.model.response.ResponseUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.asj.listed.exceptions.response.ErrorResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Slf4j
@ControllerAdvice
public class AuthorizationHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(),
                ErrorResponse.build(HttpStatus.FORBIDDEN,"You do not have the permissions to access this service "));
    }
    /*Errores de autenticacion*/
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseUtils.buildErrorResponse(HttpStatus.FORBIDDEN,"Access denied "+e.getMessage());
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        HttpStatus httpStatus;
        String errorMessage="Authentication failed";
        if (e instanceof LockedException) {
            httpStatus = HttpStatus.LOCKED;
            log.warn("User account is locked");
        } else if (e instanceof DisabledException) {
            httpStatus = HttpStatus.FORBIDDEN;
            log.warn("User account is disabled");
        } else if (e instanceof AccountExpiredException) {
            httpStatus = HttpStatus.FORBIDDEN;
            log.warn("User account has expired");
        } else if (e instanceof CredentialsExpiredException) {
            httpStatus = HttpStatus.FORBIDDEN;
            log.warn("User credentials have expired");
        } else if (e instanceof BadCredentialsException) {
            httpStatus = HttpStatus.FORBIDDEN;
            log.warn("Wrong credentials");
        } else {
            httpStatus = HttpStatus.FORBIDDEN;
            log.warn("Unknown authentication error");
        }
        return ResponseUtils.buildErrorResponse(httpStatus,errorMessage);
    }
}