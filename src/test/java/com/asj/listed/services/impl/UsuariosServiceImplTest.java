package com.asj.listed.services.impl;

import com.asj.listed.model.dto.UsuarioDTO;
import com.asj.listed.model.entities.Usuario;
import com.asj.listed.datos.datosDummy;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.mapper.UsuariosMapper;
import com.asj.listed.model.response.UsuarioResponse;
import com.asj.listed.repositories.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class UsuariosServiceImplTest {
    @MockBean
    private UsuariosRepository repo;
    @Autowired
    private UsuariosServiceImpl service;
    @MockBean
    private UsuariosMapper mapper;
    @Mock
    private PasswordEncoder pE;
    @BeforeEach
    void setUp(){
    }
    @Test
    void listarTodos() throws ErrorProcessException {
        //GIVEN
        when(this.repo.findAll())
                .thenReturn(Arrays.asList(
                        datosDummy.getUsuarioAdmin()
                ));
        //WHEN
        List<UsuarioResponse> usuarioDTOList = service.findAll();
        //THEN
        assertThat(usuarioDTOList.isEmpty()).isFalse();
        assertThat(usuarioDTOList.size()).isEqualTo(1);
        verify(repo).findAll();
    }

    @Test
    void buscarPorId() throws ErrorProcessException {
        UsuarioDTO usuarioDTO = datosDummy.getUsuarioAdminDTO();
        this.repo.save(datosDummy.getUsuarioAdmin());
        //GIVEN
        when(this.repo.findById(usuarioDTO.getId())
        ).thenReturn( Optional.of(
                datosDummy.getUsuarioAdmin()
        ));
        //WHEN
        UsuarioResponse usuarioEncontrado = this.service.findById(usuarioDTO.getId());
        //THEN
        assertThat(usuarioEncontrado);
        verify(repo).findById(usuarioDTO.getId());
    }

    @Test
    void crear() throws ErrorProcessException {
        UsuarioDTO usuarioDTO = datosDummy.getUsuarioAdminDTO();
        service.add(usuarioDTO);
        //GIVEN
        when(
                this.repo.save(
                        mapper.usuariosDTOToUsuariosEntity(usuarioDTO)
                )
        ).thenReturn(mapper.usuariosDTOToUsuariosEntity(usuarioDTO));
        //THEN
        ArgumentCaptor<Usuario> userArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(repo).save(userArgumentCaptor.capture());
        Usuario usuarioCaptor = userArgumentCaptor.getValue();

        assertThat(usuarioCaptor).isEqualTo(mapper.usuariosDTOToUsuariosEntity( datosDummy.getUsuarioAdminDTO()));
        verify(repo).findByUsuario(anyString());
    }

    @Test
    void actualizar() {
    }

    @Test
    void eliminar() {
    }

    @Test
    void buscarUsuario() {
    }
}