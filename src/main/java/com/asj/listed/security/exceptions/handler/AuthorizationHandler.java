package com.asj.listed.security.exceptions.handler;

import com.asj.listed.exceptions.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
public class AuthorizationHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(),
                new ErrorResponse("You do not have the permissions to access this service ", HttpStatus.FORBIDDEN.value()));
    }
    /*Errores de autenticacion*/
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse("Access denied "+e.getMessage(), HttpStatus.FORBIDDEN.value()));
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        String errorMessage;
        if (e instanceof LockedException) {
            httpStatus = HttpStatus.LOCKED;
            errorMessage = "User account is locked";
        } else if (e instanceof DisabledException) {
            httpStatus = HttpStatus.FORBIDDEN;
            errorMessage = "User account is disabled";
        } else if (e instanceof AccountExpiredException) {
            httpStatus = HttpStatus.FORBIDDEN;
            errorMessage = "User account has expired";
        } else if (e instanceof CredentialsExpiredException) {
            httpStatus = HttpStatus.FORBIDDEN;
            errorMessage = "User credentials have expired";
        } else {
            errorMessage = "Authentication failed: " + e.getMessage();
        }
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(errorMessage, httpStatus.value()));
    }
}