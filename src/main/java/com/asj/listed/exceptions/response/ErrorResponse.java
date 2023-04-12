package com.asj.listed.exceptions.response;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("error")
public class ErrorResponse {
    public static final String ERROR_NOT_FOUND = "An error occurred in the process: ";
    private String message;
    private int code;

    public ErrorResponse(Exception e, int code) {
        this(e.getMessage(), code);
    }

    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}