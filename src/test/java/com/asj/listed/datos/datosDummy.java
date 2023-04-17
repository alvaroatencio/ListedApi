package com.asj.listed.datos;

import com.asj.listed.model.request.UserRequest;
import com.asj.listed.model.entities.Account;
import com.asj.listed.model.entities.User;

import java.util.ArrayList;
import java.util.List;

import static com.asj.listed.model.enums.Rol.ADMIN;

public class datosDummy {
    public static User getUsuarioAdmin() {
        List<Account> accounts =new ArrayList<>();
        return new User(5L, "Administrador", "admin@gmail.com", "123456789",12345,ADMIN, accounts);
    }
    public static UserRequest getUsuarioAdminDTO() {
        return new UserRequest(5L, "Administrador", "admin@gmail.com", "123456789",12345,ADMIN);
    }
}