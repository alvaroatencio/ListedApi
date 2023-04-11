package com.asj.listed.services.impl;

import com.asj.listed.business.dto.CuentaDTO;
import com.asj.listed.business.entities.Cuenta;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.mapper.CuentasMapper;
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
    public List<CuentaDTO> findAll() {
        List<Cuenta> cuentas = repo.findAll();
        return cuentas.stream().map(mapper::cuentasEntityToCuentasDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CuentaDTO> buscarPorId(long id) {
        Optional<Cuenta> cuentas = repo.findById(id);
        return cuentas.map(mapper::cuentasEntityToCuentasDTO);
    }

    @Override
    public CuentaDTO crear(CuentaDTO cuentaDTO) {
        Cuenta cuenta = mapper.cuentasDTOToCuentasEntity(cuentaDTO);
        log.info("Creando cuenta: {}", cuentaDTO);
        return mapper.cuentasEntityToCuentasDTO(repo.save(cuenta));
    }

    @Override
    public CuentaDTO actualizar(long id, CuentaDTO cuentaDTO) {
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
            return mapper.cuentasEntityToCuentasDTO(repo.save(cuenta));
        } else {
            throw new RuntimeException("Usuario con id " + id + " no existe");
        }
    }

    @Override
    public Optional<CuentaDTO> eliminar(long id) {
        Optional<Cuenta> cuentaAEliminar = repo.findById(id);
        if (cuentaAEliminar.isPresent()) {
            Cuenta usuarioEliminado = cuentaAEliminar.get();
            repo.delete(usuarioEliminado);
            return Optional.of(mapper.cuentasEntityToCuentasDTO(usuarioEliminado));
        } else {
            throw new NotFoundException(Cuenta.class, String.valueOf(id));
        }
    }

    @Override
    public List<CuentaDTO> buscarPorId_usuario(long id_usuario) {
        List<Cuenta> cuentas = repo.findByUsuario_Id(id_usuario);
        List<CuentaDTO> cuentasDTO = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
                CuentaDTO cuentaDTO = mapper.cuentasEntityToCuentasDTO(cuenta); // convertir entidad a DTO
                cuentasDTO.add(cuentaDTO); // agregar DTO a la lista de cuentas DTO
            }
        return cuentasDTO;
    }
}
