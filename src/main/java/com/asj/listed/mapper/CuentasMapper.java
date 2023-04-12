package com.asj.listed.mapper;

import com.asj.listed.model.dto.CuentaDTO;
import com.asj.listed.model.entities.Cuenta;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CuentasMapper {
    CuentaDTO cuentasEntityToCuentasDTO(Cuenta cuenta);
    Cuenta cuentasDTOToCuentasEntity(CuentaDTO cuentaDTO);
}
