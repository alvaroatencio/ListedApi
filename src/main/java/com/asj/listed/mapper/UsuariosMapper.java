package com.asj.listed.mapper;

import com.asj.listed.business.dto.UsuariosDTO;
import com.asj.listed.business.entities.Usuarios;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UsuariosMapper {
        UsuariosDTO usuariosEntityToUsuariosDTO(Usuarios usuario);
        Usuarios usuariosDTOToUsuariosEntity(UsuariosDTO usuarioDTO);

}
