package com.asj.listed.repositories;

import com.asj.listed.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsuario(String usuario);
    Optional<User> findByMail(String mail);
    Optional<User> findByUsuarioOrMail(String usuario, String mail);

}
