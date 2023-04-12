package com.asj.listed.services.impl;
import com.asj.listed.model.dto.UsuarioDTO;
import com.asj.listed.model.entities.Usuario;
import com.asj.listed.exceptions.CreateDuplicatedException;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.mapper.UsuariosMapper;
import com.asj.listed.model.response.CuentasResponse;
import com.asj.listed.model.response.UsuarioResponse;
import com.asj.listed.repositories.UsuariosRepository;
import com.asj.listed.services.intefaces.UsuariosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.asj.listed.exceptions.response.ErrorResponse.ERROR_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuariosServiceImpl implements UsuariosService {
    private final UsuariosRepository userRepository;
    private final UsuariosMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioResponse> findAll() throws ErrorProcessException {
        log.info("Buscando todos los usuarios");
        List<UsuarioResponse> usuarios = userRepository.findAll().stream()
                .map(UsuarioResponse::toResponse)
                .collect(Collectors.toList());
        log.info("Usuarios encontrados: {}", usuarios.size());
        return usuarios;
    }
    @Override
    public UsuarioResponse findById(long id) throws ErrorProcessException {
        Usuario usuario = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        try {
            return UsuarioResponse.toResponse(usuario);
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public UsuarioResponse add(UsuarioDTO usuarioDTO) throws ErrorProcessException {
        if (userRepository.findByUsuario(usuarioDTO.getUsuario()).isPresent())throw new CreateDuplicatedException("Usuario");
        if (userRepository.findByMail(usuarioDTO.getMail()).isPresent())throw new CreateDuplicatedException("Mail");
        String passwordCifrado = passwordEncoder.encode(usuarioDTO.getPassword());
        usuarioDTO.setPassword(passwordCifrado);
        Usuario usuario = mapper.usuariosDTOToUsuariosEntity(usuarioDTO);
        log.info("Creando usuario: {}", usuarioDTO.getUsuario());
        try {
            return UsuarioResponse.toResponse(userRepository.save(usuario));
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public UsuarioResponse update(long id, UsuarioDTO usuarioDTO) throws ErrorProcessException {
        Optional<Usuario> optionalUsuario = userRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            if (!StringUtils.isEmpty(usuarioDTO.getUsuario())) {
                usuario.setUsuario(usuarioDTO.getUsuario());
                log.info("Actualizando usuario con id: {} con nuevo usuario: {}", usuario.getId(), usuario.getUsuario());
            }
            if (!StringUtils.isEmpty(usuarioDTO.getMail())) {
                usuario.setMail(usuarioDTO.getMail());
                log.info("Actualizando usuario con id: {} con nuevo mail: {}", usuario.getId(), usuario.getMail());
            }
            if (!StringUtils.isEmpty(usuarioDTO.getPassword())) {
                String passwordCifrado = passwordEncoder.encode(usuarioDTO.getPassword());
                usuario.setPassword(passwordCifrado);
                log.info("Actualizando usuario con id: \"+id+\" con nueva contraseña");
            }
            try {
                return UsuarioResponse.toResponse(userRepository.save(usuario));
            }catch (RuntimeException e){
                throw  new ErrorProcessException(e.getMessage());
            }
        } else {
            throw new NotFoundException("Usuario con id " + id + " no existe");
        }
    }

    @Override
    public UsuarioResponse delete(long id) throws  ErrorProcessException{
        Usuario usuarioAEliminar = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        try {
            userRepository.delete(usuarioAEliminar);
            return UsuarioResponse.toResponse(usuarioAEliminar);
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }
    @Override
    public UsuarioResponse buscarUsuario(String usuarioOmail) throws ErrorProcessException{
        Usuario usuario = userRepository.findByUsuarioOrMail(usuarioOmail,usuarioOmail).orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
        try {
            return UsuarioResponse.toResponse(usuario);
        }catch(RuntimeException e){
            throw new ErrorProcessException(ERROR_NOT_FOUND + e.getMessage());
        }
    }

}
