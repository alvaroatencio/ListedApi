package com.asj.listed.services.intefaces;

import com.asj.listed.business.dto.UsuarioDTO;
import com.asj.listed.repositories.Repositories;

public interface UsuariosService extends Repositories<UsuarioDTO> {
    UsuarioDTO buscarUsuario(String usuarioOmail);
}
