package com.asj.listed.services.intefaces;

import com.asj.listed.business.dto.UsuariosDTO;
import com.asj.listed.business.entities.Usuarios;
import com.asj.listed.repositories.Repositories;

public interface UsuariosService extends Repositories<UsuariosDTO> {
    // NO SE SI ESTOS COMMENT ESTAN BIEN
    //todo aca van las interacciones con la db
    //todo metodos del crud
    boolean usuarioExistente(String usuario);
    boolean mailExistente(String usuario);
}
