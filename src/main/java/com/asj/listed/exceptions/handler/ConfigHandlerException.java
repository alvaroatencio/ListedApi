package com.asj.listed.exceptions.handler;

import com.asj.listed.exceptions.BadRequestException;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConfigHandlerException {
    /*Errores de autenticacion*/
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
        return ResponseEntity.status(httpStatus).body(buildResponse(errorMessage, httpStatus));
    }
    /*Elementos no encontrados */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleEnteredDataNotFound(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildResponse(e.getMessage(), HttpStatus.NOT_FOUND));
    }
    /* 500 */
    @ExceptionHandler(ErrorProcessException.class)
    public ResponseEntity<?> handleEnteredDataConflict(ErrorProcessException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
    /* 400 */
    @ExceptionHandler({BadRequestException.class
            ,MethodArgumentNotValidException.class
    })
    public ResponseEntity<?> handleBadRequest(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }/*
    /* validation constroller request */
    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> argumentNotValidationRequest(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResponse(e.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST));
    }*/
    private static ErrorResponse buildResponse(String message, HttpStatus httpStatus) {
        return new ErrorResponse(message, httpStatus.value());
    }

}