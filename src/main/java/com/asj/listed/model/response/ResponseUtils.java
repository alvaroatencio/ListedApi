package com.asj.listed.model.response;

import com.asj.listed.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtils {
    public ResponseEntity<?> buildResponse(HttpStatus status, String message, Object data) {
        GenericResponse<Object> response = new GenericResponse<>();
        response.setMessage(message);
        response.setData(data);
        return ResponseEntity.status(status).body(response);
    }
    public static <T> ResponseEntity<?> buildErrorResponse(HttpStatus status, String errorMessage) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(errorMessage)
                .code(status.value())
                .build();
        return ResponseEntity.status(status).body(errorResponse);
    }
}
