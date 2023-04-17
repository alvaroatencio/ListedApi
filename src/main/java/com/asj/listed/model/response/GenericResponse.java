package com.asj.listed.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> GenericResponse build(boolean success, String message, T data) {
        GenericResponse response = new GenericResponse();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
