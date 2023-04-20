package com.asj.listed.repositories;

import com.asj.listed.model.entities.User;
import com.asj.listed.datos.datosDummy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repo;
    @Test
    void findByUsuario() {
        //GIVEN
        this.repo.save(datosDummy.getAdmin());
        //WHEN
        Optional<User> usuarioOptional = this.repo.findByUsuario(datosDummy.getAdmin().getUsuario());
        //THEN
        assertThat(usuarioOptional.isPresent()).isTrue();
        assertThat(usuarioOptional.get().getUsuario()).isEqualTo(datosDummy.getAdmin().getUsuario());
    }

    @Test
    void findByMail() {
        //GIVEN
        this.repo.save(datosDummy.getAdmin());
        //WHEN
        Optional<User> usuarioOptional = this.repo.findByMail(datosDummy.getAdmin().getMail());
        //THEN
        assertThat(usuarioOptional.isPresent()).isTrue();
        assertThat(usuarioOptional.get().getMail()).isEqualTo(datosDummy.getAdmin().getMail());
    }

    @Test
    void findByUsuarioOrMail() {
        //GIVEN
        this.repo.save(datosDummy.getAdmin());
        //WHEN
        Optional<User> usuarioOptional = this.repo.findByUsuarioOrMail(datosDummy.getAdmin().getUsuario(),datosDummy.getAdmin().getMail());
        //THEN
        assertThat(usuarioOptional.isPresent()).isTrue();
        assertThat(usuarioOptional.get().getUsuario()).isEqualTo(datosDummy.getAdmin().getUsuario());
        assertThat(usuarioOptional.get().getMail()).isEqualTo(datosDummy.getAdmin().getMail());
    }
}