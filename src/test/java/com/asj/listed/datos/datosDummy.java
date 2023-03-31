package com.asj.listed.datos;

import com.asj.listed.business.dto.UsuarioDTO;
import com.asj.listed.business.entities.Cuenta;
import com.asj.listed.business.entities.Usuario;

import java.util.ArrayList;
import java.util.List;

import static com.asj.listed.business.enums.Rol.ADMIN;

public class datosDummy {
    public static Usuario getUsuarioAdmin() {
        List<Cuenta> cuentas=new ArrayList<>();
        return new Usuario(5L, "Administrador", "admin@gmail.com", "123456789",12345,ADMIN,cuentas);
    }
    public static UsuarioDTO getUsuarioAdminDTO() {
        return new UsuarioDTO(5L, "Administrador", "admin@gmail.com", "123456789",12345,ADMIN);
    }
}