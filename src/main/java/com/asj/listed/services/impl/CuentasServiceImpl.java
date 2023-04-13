package com.asj.listed.services.impl;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.model.dto.CuentaDTO;
import com.asj.listed.model.entities.Cuenta;
import com.asj.listed.model.response.CuentasResponse;
import com.asj.listed.repositories.CuentasRepository;
import com.asj.listed.services.intefaces.CuentasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.asj.listed.exceptions.response.ErrorResponse.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuentasServiceImpl implements CuentasService {
    private final CuentasRepository cuentaRepository;
    @Override
    public List<CuentasResponse> findAll() throws ErrorProcessException {
        try {
            log.info("Searching for all accounts");
            List<CuentasResponse> cuentas = cuentaRepository.findAll()
                    .stream()
                    .map(CuentasResponse::toResponse)
                    .collect(Collectors.toList());
            log.info("Found accounts: {}", cuentas.size());
            return cuentas;
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public CuentasResponse findById(long id) throws ErrorProcessException {
        Cuenta cuentas = cuentaRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with id " + id + " does not exist"));
        try {
            return CuentasResponse.toResponse(cuentas);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    //// TODO: 12/4/2023  ARREGLAR EN ESTOS METODOS LAS RELACIONES
    @Override
    public CuentasResponse add(CuentaDTO cuentaDTO) throws ErrorProcessException {
        log.info("Account creation request received");
        try {
            log.info("Creating account: {}", cuentaDTO);
            return CuentasResponse.toResponse(cuentaRepository.save(CuentaDTO.toEntity(cuentaDTO)));
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public CuentasResponse update(long id, CuentaDTO cuentaDTO) throws ErrorProcessException {
        log.info("Request for changes on account with id: " + id);
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with id " + id + " does not exist"));
        if (!StringUtils.isEmpty(cuentaDTO.getNroCuenta()))
            cuenta.setNroCuenta(cuentaDTO.getNroCuenta());
        if (!StringUtils.isEmpty(cuentaDTO.getCbu()))
            cuenta.setCbu(cuentaDTO.getCbu());
        if (!StringUtils.isEmpty(cuentaDTO.getSucursal()))
            cuenta.setSucursal(cuenta.getSucursal());
        if (!StringUtils.isEmpty(cuentaDTO.getBanco()))
            cuenta.setBanco(cuentaDTO.getBanco());
        if (!StringUtils.isEmpty(cuentaDTO.getAlias()))
            cuenta.setAlias(cuentaDTO.getAlias());
        try {
            return CuentasResponse.toResponse(cuentaRepository.save(cuenta));
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public CuentasResponse delete(long id) throws ErrorProcessException {
        Cuenta cuentaAEliminar = cuentaRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with id " + id + " does not exist"));
        try {
            cuentaRepository.delete(cuentaAEliminar);
            return CuentasResponse.toResponse(cuentaAEliminar);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public List<CuentasResponse> buscarPorId_usuario(long id_usuario) {
        List<CuentasResponse> cuentas = cuentaRepository.findByUsuario_Id(id_usuario);
        List<CuentasResponse> cuentasResponse = new ArrayList<>();
        for (CuentasResponse cuenta : cuentas) {
            cuentasResponse.add(cuenta); // agregar DTO a la lista de cuentas DTO
        }
        return cuentasResponse;
    }
}
