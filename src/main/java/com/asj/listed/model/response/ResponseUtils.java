package com.asj.listed.model.response;

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

}
