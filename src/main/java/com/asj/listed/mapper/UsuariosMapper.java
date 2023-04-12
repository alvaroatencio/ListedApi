package com.asj.listed.mapper;

import com.asj.listed.model.dto.UsuarioDTO;
import com.asj.listed.model.entities.Usuario;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UsuariosMapper {
    UsuarioDTO usuariosEntityToUsuariosDTO(Usuario usuario);
    Usuario usuariosDTOToUsuariosEntity(UsuarioDTO usuarioDTO);
}
