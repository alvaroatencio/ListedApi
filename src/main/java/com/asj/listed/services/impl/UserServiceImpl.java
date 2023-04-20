package com.asj.listed.services.impl;

import com.asj.listed.exceptions.inherited.FieldAlreadyExist;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.model.request.UserRequest;
import com.asj.listed.model.entities.User;
import com.asj.listed.model.response.UserResponse;
import com.asj.listed.repositories.UserRepository;
import com.asj.listed.services.intefaces.UserService;
import jakarta.transaction.Transactional;
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<UserResponse> findAll() throws ErrorProcessException {
        try {
            List<UserResponse> usuarios = userRepository.findAll()
                    .stream()
                    .map(UserResponse::toResponse)
                    .collect(Collectors.toList());
            return usuarios;
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public UserResponse findById(long id) throws ErrorProcessException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id "+id+" does not exist"));
        try {
            return UserResponse.toResponse(user);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    //// TODO: 12/4/2023  ARREGLAR EN ESTOS METODOS LAS RELACIONES
    @Override
    public UserResponse add(UserRequest userRequest) throws ErrorProcessException {
        log.info("User creation request received");
        userRepository.findByUsuario(userRequest.getUsuario())
                .ifPresent(usuario -> { throw new FieldAlreadyExist("User"); });
        userRepository.findByMail(userRequest.getMail())
                .ifPresent(usuario -> { throw new FieldAlreadyExist("Email"); });
        String passwordCifrado = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(passwordCifrado);
        log.info("Creating user: {}", userRequest.getUsuario());
        try {
            return UserResponse.toResponse(userRepository.save(UserRequest.toEntity(userRequest)));
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public UserResponse update(long id, UserRequest userRequest) throws ErrorProcessException {
        log.info("Request for changes on user with id: "+ id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
        if (!StringUtils.isEmpty(userRequest.getUsuario()))
            user.setUsuario(userRequest.getUsuario());
        if (!StringUtils.isEmpty(userRequest.getMail()))
            user.setMail(userRequest.getMail());
        if (!StringUtils.isEmpty(userRequest.getPassword())) {
            String passwordCifrado = passwordEncoder.encode(userRequest.getPassword());
            user.setPassword(passwordCifrado);
            log.info("Updating user with id: "+ id +" with new password");
        }
        try {
            return UserResponse.toResponse(userRepository.save(user));
        } catch (RuntimeException e) {
            throw new ErrorProcessException(e.getMessage());
        }
    }
    @Override
    public UserResponse delete(long id) throws ErrorProcessException {
        User userAEliminar = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " does not exist"));
        try {
            userRepository.delete(userAEliminar);
            return UserResponse.toResponse(userAEliminar);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public UserResponse buscarUsuario(String userOrEmail) throws ErrorProcessException {
        User user = userRepository.findByUsuarioOrMail(userOrEmail, userOrEmail).orElseThrow(() -> new NotFoundException("User not found"));
        try {
            return UserResponse.toResponse(user);
        } catch (RuntimeException e) {
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
}