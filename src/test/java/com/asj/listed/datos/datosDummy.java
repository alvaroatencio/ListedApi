package com.asj.listed.datos;

import com.asj.listed.model.enums.Rol;
import com.asj.listed.model.request.UserRequest;
import com.asj.listed.model.entities.Account;
import com.asj.listed.model.entities.User;

import java.util.ArrayList;
import java.util.List;

import static com.asj.listed.model.enums.Rol.ADMIN;

public class datosDummy {
    public static User getAdmin() {
        List<Account> accounts =new ArrayList<>();
        return new User(100L, "testUser", "test@mail", "testPassword",100000, Rol.ADMIN, accounts);
    }
    public static User getUser() {
        List<Account> accounts =new ArrayList<>();
        return new User(100L, "testUser", "test@mail", "testPassword",100000, Rol.USUARIO, accounts);
    }
    public static UserRequest getAdminRequest() {
        return new UserRequest(100L, "testUser", "test@mail", "testPassword",100000, Rol.ADMIN);
    }
    public static UserRequest getUserRequest() {
        return new UserRequest(100L, "testUser", "test@mail", "testPassword",100000, Rol.USUARIO);
    }
}