package com.asj.listed.services.impl;

import com.asj.listed.exceptions.DuplicatedEntityException;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.mapper.UsuariosMapper;
import com.asj.listed.model.dto.UsuarioRequest;
import com.asj.listed.model.entities.Usuario;
import com.asj.listed.model.response.UsuarioResponse;
import com.asj.listed.repositories.UsuariosRepository;
import com.asj.listed.services.intefaces.UsuariosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.asj.listed.exceptions.response.ErrorResponse.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuariosServiceImpl implements UsuariosService {
    private final UsuariosRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<UsuarioResponse> findAll() throws ErrorProcessException {
        try {
            log.info("Searching for all users");
            List<UsuarioResponse> usuarios = userRepository.findAll()
                    .stream()
                    .map(UsuarioResponse::toResponse)
                    .collect(Collectors.toList());
            log.info("Users found: {}", usuarios.size());
            return usuarios;
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public UsuarioResponse findById(long id) throws ErrorProcessException {
        Usuario usuario = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id "+id+" does not exist"));
        try {
            return UsuarioResponse.toResponse(usuario);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    //// TODO: 12/4/2023  ARREGLAR EN ESTOS METODOS LAS RELACIONES
    @Override
    public UsuarioResponse add(UsuarioRequest usuarioRequest) throws ErrorProcessException {
        log.info("User creation request received");
        userRepository.findByUsuario(usuarioRequest.getUsuario())
                .ifPresent(usuario -> { throw new DuplicatedEntityException("User"); });
        userRepository.findByMail(usuarioRequest.getMail())
                .ifPresent(usuario -> { throw new DuplicatedEntityException("Email"); });
        String passwordCifrado = passwordEncoder.encode(usuarioRequest.getPassword());
        usuarioRequest.setPassword(passwordCifrado);
        log.info("Creating user: {}", usuarioRequest.getUsuario());
        try {
            return UsuarioResponse.toResponse(userRepository.save(UsuarioRequest.toEntity(usuarioRequest)));
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public UsuarioResponse update(long id, UsuarioRequest usuarioRequest) throws ErrorProcessException {
        log.info("Request for changes on user with id: "+ id);
        Usuario usuario = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
        if (!StringUtils.isEmpty(usuarioRequest.getUsuario()))
            usuario.setUsuario(usuarioRequest.getUsuario());
        if (!StringUtils.isEmpty(usuarioRequest.getMail()))
            usuario.setMail(usuarioRequest.getMail());
        if (!StringUtils.isEmpty(usuarioRequest.getPassword())) {
            String passwordCifrado = passwordEncoder.encode(usuarioRequest.getPassword());
            usuario.setPassword(passwordCifrado);
            log.info("Updating user with id: "+ id +" with new password");
        }
        try {
            return UsuarioResponse.toResponse(userRepository.save(usuario));
        } catch (RuntimeException e) {
            throw new ErrorProcessException(e.getMessage());
        }
    }
    @Override
    public UsuarioResponse delete(long id) throws ErrorProcessException {
        Usuario usuarioAEliminar = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
        try {
            userRepository.delete(usuarioAEliminar);
            return UsuarioResponse.toResponse(usuarioAEliminar);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public UsuarioResponse buscarUsuario(String userOrEmail) throws ErrorProcessException {
        Usuario usuario = userRepository.findByUsuarioOrMail(userOrEmail, userOrEmail).orElseThrow(() -> new NotFoundException("User not found"));
        try {
            return UsuarioResponse.toResponse(usuario);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
}