package com.asj.listed.controllers;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.response.ErrorResponse;
import com.asj.listed.model.entities.User;
import com.asj.listed.model.request.UserRequest;
import com.asj.listed.datos.datosDummy;
import com.asj.listed.services.intefaces.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.MockitoJUnitRunner;

//@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
/*
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserRequest userRequest;
    private User user;

    @Before("")
    public void setup() {
        userRequest = datosDummy.getAdminRequest();
        user = datosDummy.getAdmin();
    }

    @Test
    public void testAddUserSuccess() throws ErrorProcessException {
        Mockito.when(userService.add(userRequest)).thenReturn(user);

        ResponseEntity<?> response = userController.addUser(userRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof GenericResponse);
        GenericResponse genericResponse = (GenericResponse) response.getBody();
        assertTrue(genericResponse.isSuccess());
        assertEquals("Usuario creado exitosamente", genericResponse.getMessage());
        assertEquals(user, genericResponse.getData());
        Mockito.verify(userService, Mockito.times(1)).add(userRequest);
    }

    @Test
    public void testAddUserError() throws ErrorProcessException {
        String errorMessage = "Error al crear usuario";
        Mockito.when(userService.add(userRequest)).thenThrow(new ErrorProcessException(errorMessage));

        ResponseEntity<?> response = userController.addUser(userRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertFalse(errorResponse.isSuccess());
        assertEquals(errorMessage, errorResponse.getMessage());
        Mockito.verify(userService, Mockito.times(1)).add(userRequest);
    }

    @Test
    public void testAddUserBadRequest() throws ErrorProcessException {
        userRequest.setMail("invalid_email_format");

        ResponseEntity<?> response = userController.addUser(userRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertFalse(errorResponse.isSuccess());
        assertEquals("Invalid email format", errorResponse.getMessage());
        Mockito.verify(userService, Mockito.never()).add(userRequest);
    }*/
}