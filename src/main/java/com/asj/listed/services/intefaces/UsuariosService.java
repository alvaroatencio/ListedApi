package com.asj.listed.services.intefaces;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.dto.UsuarioDTO;
import com.asj.listed.model.response.UsuarioResponse;
import com.asj.listed.repositories.Repositories;

public interface UsuariosService extends Repositories<UsuarioResponse,UsuarioDTO> {
    UsuarioResponse buscarUsuario(String usuarioOmail) throws ErrorProcessException;
}
