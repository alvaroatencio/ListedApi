package com.asj.listed.services.impl;

import com.asj.listed.model.request.UserRequest;
import com.asj.listed.datos.datosDummy;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.model.response.UserResponse;
import com.asj.listed.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class UserServiceImplTest {
    @MockBean
    private UserRepository repo;
    @Autowired
    private UserServiceImpl service;
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
        List<UserResponse> usuarioDTOList = service.findAll();
        //THEN
        assertThat(usuarioDTOList.isEmpty()).isFalse();
        assertThat(usuarioDTOList.size()).isEqualTo(1);
        verify(repo).findAll();
    }

    @Test
    void buscarPorId() throws ErrorProcessException {
        UserRequest userRequest = datosDummy.getUsuarioAdminDTO();
        this.repo.save(datosDummy.getUsuarioAdmin());
        //GIVEN
        when(this.repo.findById(userRequest.getId())
        ).thenReturn( Optional.of(
                datosDummy.getUsuarioAdmin()
        ));
        //WHEN
        UserResponse usuarioEncontrado = this.service.findById(userRequest.getId());
        //THEN
        assertThat(usuarioEncontrado);
        verify(repo).findById(userRequest.getId());
    }
/*
    @Test
    void crear() throws ErrorProcessException {
        UsuarioRequest usuarioRequest = datosDummy.getUsuarioAdminDTO();
        service.add(usuarioRequest);
        //GIVEN
        when(
                this.repo.save(
                        mapper.usuariosDTOToUsuariosEntity(usuarioRequest)
                )
        ).thenReturn(mapper.usuariosDTOToUsuariosEntity(usuarioRequest));
        //THEN
        ArgumentCaptor<Usuario> userArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(repo).save(userArgumentCaptor.capture());
        Usuario usuarioCaptor = userArgumentCaptor.getValue();

        assertThat(usuarioCaptor).isEqualTo(mapper.usuariosDTOToUsuariosEntity( datosDummy.getUsuarioAdminDTO()));
        verify(repo).findByUsuario(anyString());
    }*/

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