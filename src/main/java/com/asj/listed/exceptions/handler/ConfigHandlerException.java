package com.asj.listed.exceptions.handler;

import com.asj.listed.exceptions.BadRequestException;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.model.response.ResponseUtils;
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
        return ResponseUtils.buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    }
    /* 400 */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleEnteredDataNotFound(NotFoundException e){
        return ResponseUtils.buildErrorResponse(HttpStatus.NOT_FOUND,e.getMessage());
    }
    @ExceptionHandler({BadRequestException.class
            ,MethodArgumentNotValidException.class
    })
    public ResponseEntity<?> handleBadRequest(BadRequestException e){
        return ResponseUtils.buildErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage());
    }
}