package com.asj.listed.mapper;

import com.asj.listed.business.dto.CuentaDTO;
import com.asj.listed.business.entities.Cuenta;
import com.asj.listed.business.entities.Titular;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CuentasMapper {
    CuentaDTO cuentasEntityToCuentasDTO(Cuenta cuenta);
    Cuenta cuentasDTOToCuentasEntity(CuentaDTO cuentaDTO);
}
