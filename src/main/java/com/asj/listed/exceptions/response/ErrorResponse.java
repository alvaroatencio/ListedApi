package com.asj.listed.exceptions.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import org.springframework.http.HttpStatus;

@JsonRootName("error")
@Data
public class ErrorResponse {
    public static final String ERROR_NOT_FOUND = "An error occurred in the process: ";
    private String message;
    private int code;
    public ErrorResponse(Exception e, HttpStatus code) {
        this(e.getMessage(), code.value());
    }
    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }
}