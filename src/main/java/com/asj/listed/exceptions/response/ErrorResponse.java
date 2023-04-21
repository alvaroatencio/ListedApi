package com.asj.listed.exceptions.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResponse {
    public static final String ERROR_NOT_FOUND = "An error occurred in the process: ";
    private String message;
    private int code;
    public static ErrorResponse build(HttpStatus httpStatus, String message) {
        return ErrorResponse.builder()
                .message(message)
                .code(httpStatus.value())
                .build();
    }
}