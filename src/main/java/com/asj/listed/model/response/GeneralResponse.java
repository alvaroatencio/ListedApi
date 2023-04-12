package com.asj.listed.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> GeneralResponse build(boolean success, String message, T data) {
        GeneralResponse response = new GeneralResponse();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
