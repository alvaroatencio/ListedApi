package com.asj.listed.services.impl;

import com.asj.listed.business.dto.CuentasDTO;
import com.asj.listed.business.entities.Cuentas;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.mapper.CuentasMapper;
import com.asj.listed.repositories.CuentasRepository;
import com.asj.listed.services.intefaces.CuentasService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CuentasServiceImpl implements CuentasService {
    private final CuentasRepository repo;
    private final CuentasMapper mapper;

    public CuentasServiceImpl(CuentasRepository repo, @Qualifier("cuentasMapperImpl") CuentasMapper mapper) {
        this.repo=repo;
        this.mapper = mapper;
    }

    @Override
    public List<CuentasDTO> listarTodos() {
        List<Cuentas> cuentas = repo.findAll();
        return cuentas.stream().map(mapper::cuentasEntityToCuentasDTO).collect(Collectors.toList());
    }
    @Override
    public Optional<CuentasDTO> buscarPorId(int id) {
        Optional<Cuentas> cuentas = repo.findById(id);
        return cuentas.map(mapper::cuentasEntityToCuentasDTO);
    }
    @Override
    public CuentasDTO crear(CuentasDTO cuentaDTO) {
        Cuentas cuenta = mapper.cuentasDTOToCuentasEntity(cuentaDTO);
        log.info("Creando cuenta: {}", cuentaDTO);
        return mapper.cuentasEntityToCuentasDTO(repo.save(cuenta));
    }
    @Override
    public CuentasDTO actualizar(int id, CuentasDTO cuentaDTO) {
        Optional<Cuentas> optionalCuenta = repo.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuentas cuenta = optionalCuenta.get();
            if (!StringUtils.isEmpty(cuentaDTO.getNro_cuenta())) {
                cuenta.setNroCuenta(cuentaDTO.getNro_cuenta());
                log.info("Actualizanda cuenta con id: "+id+" con nueva cuenta: {}", cuenta.getId(), cuenta.getNroCuenta());
            }
            if (!StringUtils.isEmpty(cuentaDTO.getCbu())) {
                cuenta.setCbu(cuentaDTO.getCbu());
                log.info("Actualizanda cuenta con id: \"+id+\" con nuevo mail: {}", cuenta.getId(), cuenta.getCbu());
            }
            if (!StringUtils.isEmpty(cuentaDTO.getSucursal())) {
                cuenta.setSucursal(cuenta.getSucursal());
                log.info("Actualizanda cuenta con id: \"+id+\" con nueva sucursal: {}",cuenta.getId(), cuenta.getSucursal());
            }
            if (!StringUtils.isEmpty(cuentaDTO.getSucursal())) {
                cuenta.setSucursal(cuenta.getSucursal());
                log.info("Actualizanda cuenta con id: \"+id+\" con nueva sucursal: {}",cuenta.getId(), cuenta.getSucursal());
            }

            //// TODO: 27/3/2023        SEGUIR CON ESTO

            //

            //

            //
            return mapper. cuentasEntityToCuentasDTO(repo.save(cuenta));
        } else {
            throw new RuntimeException("Usuario con id " + id + " no existe");
        }
    }

    @Override
    public Optional<CuentasDTO> eliminar(int id) {
        Optional<Cuentas> cuentaAEliminar = repo.findById(id);
        if (cuentaAEliminar.isPresent()) {
            Cuentas usuarioEliminado = cuentaAEliminar.get();
            repo.delete(usuarioEliminado);
            return Optional.of(mapper.cuentasEntityToCuentasDTO(usuarioEliminado));
        } else {
            throw new NotFoundException(Cuentas.class, String.valueOf(id));
        }
    }
}
