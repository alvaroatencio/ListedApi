package com.asj.listed.services.impl;

import com.asj.listed.business.dto.UsuariosDTO;
import com.asj.listed.business.entities.Usuarios;
import com.asj.listed.exceptions.CreateDuplicatedException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.mapper.UsuariosMapper;
import com.asj.listed.repositories.UsuariosRepository;
import com.asj.listed.services.intefaces.UsuariosService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//todo validaciones de db
@Service
@Slf4j
public class UsuariosServiceImpl implements UsuariosService {
    private final UsuariosRepository repo;
    private final UsuariosMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuariosServiceImpl( UsuariosRepository repo,@Qualifier("usuariosMapperImpl") UsuariosMapper mapper,PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.mapper = mapper;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    public List<UsuariosDTO> listarTodos() {
        List<Usuarios> usuarios = repo.findAll();
        return usuarios.stream().map(mapper::usuariosEntityToUsuariosDTO).collect(Collectors.toList());
    }
    @Override
    public Optional<UsuariosDTO> buscarPorId(int id) {
        Optional<Usuarios> usuario = repo.findById(id);
        return usuario.map(mapper::usuariosEntityToUsuariosDTO);
    }
    @Override
    public UsuariosDTO crear(UsuariosDTO usuarioDTO) {
        if (repo.findByUsuario(usuarioDTO.getUsuario()).isPresent())throw new CreateDuplicatedException("usuario");
        if (repo.findByMail(usuarioDTO.getMail()).isPresent())throw new CreateDuplicatedException("mail");
        String passwordCifrado = passwordEncoder.encode(usuarioDTO.getPassword());
        usuarioDTO.setPassword(passwordCifrado);
        Usuarios usuario = mapper.usuariosDTOToUsuariosEntity(usuarioDTO);
        log.info("Creando usuario: {}", usuarioDTO.getUsuario());
        return mapper.usuariosEntityToUsuariosDTO(repo.save(usuario));
    }
    @Override
    public UsuariosDTO actualizar(int id, UsuariosDTO usuarioDTO) {
        Optional<Usuarios> optionalUsuario = repo.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuarios usuario = optionalUsuario.get();
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
            return mapper.usuariosEntityToUsuariosDTO(repo.save(usuario));
        } else {
            throw new RuntimeException("Usuario con id " + id + " no existe");
        }
    }

    @Override
    public Optional<UsuariosDTO> eliminar(int id) {
        Optional<Usuarios> usuarioAEliminar = repo.findById(id);
        if (usuarioAEliminar.isPresent()) {
            Usuarios usuarioEliminado = usuarioAEliminar.get();
            repo.delete(usuarioEliminado);
            return Optional.of(mapper.usuariosEntityToUsuariosDTO(usuarioEliminado));
        } else {
            throw new NotFoundException(Usuarios.class, String.valueOf(id));
        }
    }
    @Override
    public boolean usuarioExistente(String usuario) {
        return repo.findByUsuario(usuario).isPresent();
    }
    @Override
    public boolean mailExistente(String mail) {
        return repo.findByUsuario(mail).isPresent();
    }

}
