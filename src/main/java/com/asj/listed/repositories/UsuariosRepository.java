package com.asj.listed.repositories;

import com.asj.listed.business.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsuario(String usuario);
    Optional<Usuario> findByMail(String mail);
    Optional<Usuario> findByUsuarioOrMail(String usuario, String mail);

}
