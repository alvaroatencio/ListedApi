package com.asj.listed.services.intefaces;

import com.asj.listed.business.dto.CuentaDTO;
import com.asj.listed.repositories.Repositories;

import java.util.List;
import java.util.Optional;

public interface CuentasService extends Repositories<CuentaDTO> {
    List<CuentaDTO> buscarPorId_usuario(long id);
}
