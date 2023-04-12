package com.asj.listed.repositories;

import com.asj.listed.model.entities.Usuario;
import com.asj.listed.datos.datosDummy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
class UsuariosRepositoryTest {
    @Autowired
    private UsuariosRepository repo;
    @Test
    void findByUsuario() {
        //GIVEN
        this.repo.save(datosDummy.getUsuarioAdmin());
        //WHEN
        Optional<Usuario> usuarioOptional = this.repo.findByUsuario(datosDummy.getUsuarioAdmin().getUsuario());
        //THEN
        assertThat(usuarioOptional.isPresent()).isTrue();
        assertThat(usuarioOptional.get().getUsuario()).isEqualTo(datosDummy.getUsuarioAdmin().getUsuario());
    }

    @Test
    void findByMail() {
        //GIVEN
        this.repo.save(datosDummy.getUsuarioAdmin());
        //WHEN
        Optional<Usuario> usuarioOptional = this.repo.findByMail(datosDummy.getUsuarioAdmin().getMail());
        //THEN
        assertThat(usuarioOptional.isPresent()).isTrue();
        assertThat(usuarioOptional.get().getMail()).isEqualTo(datosDummy.getUsuarioAdmin().getMail());
    }

    @Test
    void findByUsuarioOrMail() {
        //GIVEN
        this.repo.save(datosDummy.getUsuarioAdmin());
        //WHEN
        Optional<Usuario> usuarioOptional = this.repo.findByUsuarioOrMail(datosDummy.getUsuarioAdmin().getUsuario(),datosDummy.getUsuarioAdmin().getMail());
        //THEN
        assertThat(usuarioOptional.isPresent()).isTrue();
        assertThat(usuarioOptional.get().getUsuario()).isEqualTo(datosDummy.getUsuarioAdmin().getUsuario());
        assertThat(usuarioOptional.get().getMail()).isEqualTo(datosDummy.getUsuarioAdmin().getMail());
    }
}