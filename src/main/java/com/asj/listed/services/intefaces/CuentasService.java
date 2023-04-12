package com.asj.listed.services.intefaces;

import com.asj.listed.model.dto.CuentaDTO;
import com.asj.listed.model.response.CuentasResponse;
import com.asj.listed.repositories.Repositories;

import java.util.List;

public interface CuentasService extends Repositories<CuentasResponse,CuentaDTO> {
    List<CuentasResponse> buscarPorId_usuario(long id);
}
