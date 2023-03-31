package com.asj.listed.repositories;

import com.asj.listed.business.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentasRepository extends JpaRepository<Cuenta,Long> {
    List<Cuenta> findByUsuario_Id(long id_usuario);
}
