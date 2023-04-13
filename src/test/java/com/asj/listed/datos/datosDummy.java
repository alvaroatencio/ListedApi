package com.asj.listed.datos;

import com.asj.listed.model.dto.UsuarioRequest;
import com.asj.listed.model.entities.Cuenta;
import com.asj.listed.model.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

import static com.asj.listed.model.enums.Rol.ADMIN;

public class datosDummy {
    public static Usuario getUsuarioAdmin() {
        List<Cuenta> cuentas=new ArrayList<>();
        return new Usuario(5L, "Administrador", "admin@gmail.com", "123456789",12345,ADMIN,cuentas);
    }
    public static UsuarioRequest getUsuarioAdminDTO() {
        return new UsuarioRequest(5L, "Administrador", "admin@gmail.com", "123456789",12345,ADMIN);
    }
}