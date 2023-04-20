package com.asj.listed.exceptions.handler;

import com.asj.listed.exceptions.BadRequestException;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
//// TODO: 20/4/2023  ver si el sgte extends sirve
                                    //extends ResponseEntityExceptionHandler
public class ConfigHandlerException {
    /* 500 */
    @ExceptionHandler(ErrorProcessException.class)
    public ResponseEntity<?> handleEnteredDataConflict(ErrorProcessException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
    /* 400 */
        /*Elementos no encontrados */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleEnteredDataNotFound(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }
    @ExceptionHandler({BadRequestException.class
            ,MethodArgumentNotValidException.class
    })
    public ResponseEntity<?> handleBadRequest(BadRequestException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
}