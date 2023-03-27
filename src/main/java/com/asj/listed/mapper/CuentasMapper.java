package com.asj.listed.mapper;

import com.asj.listed.business.dto.CuentasDTO;
import com.asj.listed.business.entities.Cuentas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CuentasMapper {
    @Mapping(source = "id",target = "codigo")
    CuentasDTO cuentasEntityToCuentasDTO(Cuentas cuenta);
    @Mapping(source="codigo",target = "id")
    Cuentas cuentasDTOToCuentasEntity(CuentasDTO cuentaDTO);
}
