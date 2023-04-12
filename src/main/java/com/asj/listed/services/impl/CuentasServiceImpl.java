package com.asj.listed.services.impl;

import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.dto.CuentaDTO;
import com.asj.listed.model.entities.Cuenta;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.mapper.CuentasMapper;
import com.asj.listed.model.response.CuentasResponse;
import com.asj.listed.repositories.CuentasRepository;
import com.asj.listed.services.intefaces.CuentasService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.asj.listed.exceptions.response.ErrorResponse.ERROR_NOT_FOUND;

@Service
@Slf4j
public class CuentasServiceImpl implements CuentasService {
    private final CuentasRepository repo;
    private final CuentasMapper mapper;

    public CuentasServiceImpl(CuentasRepository repo, @Qualifier("cuentasMapperImpl") CuentasMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<CuentasResponse> findAll() throws ErrorProcessException {
        try {
            return repo.findAll().stream()
                    .map(CuentasResponse::toResponse)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public CuentasResponse findById(long id) throws ErrorProcessException {
        Cuenta cuentas = repo.findById(id).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        try {
            return CuentasResponse.toResponse(cuentas);
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public CuentasResponse add(CuentaDTO cuentaDTO) throws ErrorProcessException{
        Cuenta cuenta = mapper.cuentasDTOToCuentasEntity(cuentaDTO);
        log.info("Creando cuenta: {}", cuentaDTO);
        return CuentasResponse.toResponse(repo.save(cuenta));
    }

    @Override
    public CuentasResponse update(long id, CuentaDTO cuentaDTO) throws  ErrorProcessException{
        Optional<Cuenta> optionalCuenta = repo.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();
            if (!StringUtils.isEmpty(cuentaDTO.getNroCuenta())) {
                cuenta.setNroCuenta(cuentaDTO.getNroCuenta());
                log.info("Actualizanda cuenta con id: " + id + " con nueva cuenta: {}", cuenta.getId(), cuenta.getNroCuenta());
            }
            if (!StringUtils.isEmpty(cuentaDTO.getCbu())) {
                cuenta.setCbu(cuentaDTO.getCbu());
                log.info("Actualizanda cuenta con id: \"+id+\" con nuevo mail: {}", cuenta.getId(), cuenta.getCbu());
            }
            if (!StringUtils.isEmpty(cuentaDTO.getSucursal())) {
                cuenta.setSucursal(cuenta.getSucursal());
                log.info("Actualizanda cuenta con id: \"+id+\" con nueva sucursal: {}", cuenta.getId(), cuenta.getSucursal());
            }
            if (!StringUtils.isEmpty(cuentaDTO.getSucursal())) {
                cuenta.setSucursal(cuenta.getSucursal());
                log.info("Actualizanda cuenta con id: \"+id+\" con nueva sucursal: {}", cuenta.getId(), cuenta.getSucursal());
            }

            //// TODO: 27/3/2023        SEGUIR CON ESTO

            //

            //

            //
            return CuentasResponse.toResponse(repo.save(cuenta));
        } else {
            throw new RuntimeException("Usuario con id " + id + " no existe");
        }
    }

    @Override
    public CuentasResponse delete(long id) throws  ErrorProcessException{
        Cuenta cuentaAEliminar = repo.findById(id).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        try {
            repo.delete(cuentaAEliminar);
            return CuentasResponse.toResponse(cuentaAEliminar);
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

    @Override
    public List<CuentasResponse> buscarPorId_usuario(long id_usuario){
        List<CuentasResponse> cuentas = repo.findByUsuario_Id(id_usuario);
        List<CuentasResponse> cuentasResponse = new ArrayList<>();
        for (CuentasResponse cuenta : cuentas) {
                cuentasResponse.add(cuenta); // agregar DTO a la lista de cuentas DTO
            }
        return cuentasResponse;
    }
}
