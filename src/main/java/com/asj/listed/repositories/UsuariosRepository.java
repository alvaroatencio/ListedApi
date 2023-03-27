package com.asj.listed.repositories;

import com.asj.listed.business.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios,Integer> {
    Optional<Usuarios> findByUsuario(String usuario);
    Optional<Usuarios> findByMail(String mail);
    Optional<Usuarios> findByUsuarioOrMail(String usuario, String mail);

}
