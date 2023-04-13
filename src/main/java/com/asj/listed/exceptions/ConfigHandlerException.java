package com.asj.listed.exceptions;

import com.asj.listed.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConfigHandlerException {
    /*Errores de autenticacion*/
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(buildResponse(e.getMessage(), HttpStatus.UNAUTHORIZED));
    }
    /* Elementos no encontrados */
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
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST));
    }
    /* validation constroller request */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> argumentNotValidationRequest(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResponse(e.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST));
    }
    private static ErrorResponse buildResponse(String message, HttpStatus httpStatus) {
        return new ErrorResponse(message, httpStatus.value());
    }

}