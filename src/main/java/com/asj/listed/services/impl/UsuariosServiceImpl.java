package com.asj.listed.services.impl;
import com.asj.listed.business.dto.UsuarioDTO;
import com.asj.listed.business.entities.Usuario;
import com.asj.listed.exceptions.CreateDuplicatedException;
import com.asj.listed.exceptions.ErrorProcessException;
import com.asj.listed.exceptions.NotFoundException;
import com.asj.listed.mapper.UsuariosMapper;
import com.asj.listed.repositories.UsuariosRepository;
import com.asj.listed.services.intefaces.UsuariosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuariosServiceImpl implements UsuariosService {
    private final UsuariosRepository repo;
    private final UsuariosMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> findAll() throws ErrorProcessException {
        List<Usuario> usuarios = repo.findAll();
        return usuarios
                .stream()
                .map(mapper::usuariosEntityToUsuariosDTO).
                collect(Collectors.toList());
    }
    @Override
    public Optional<UsuarioDTO> buscarPorId(long id) {
        Optional<Usuario> usuario = repo.findById(id);
        return usuario.map(mapper::usuariosEntityToUsuariosDTO);
    }
    @Override
    public UsuarioDTO crear(UsuarioDTO usuarioDTO) {
        System.out.println("UsuariosServiceImpl.crear");
        if (repo.findByUsuario(usuarioDTO.getUsuario()).isPresent())throw new CreateDuplicatedException("Usuario");
        if (repo.findByMail(usuarioDTO.getMail()).isPresent())throw new CreateDuplicatedException("Mail");
        String passwordCifrado = passwordEncoder.encode(usuarioDTO.getPassword());
        usuarioDTO.setPassword(passwordCifrado);
        Usuario usuario = mapper.usuariosDTOToUsuariosEntity(usuarioDTO);
        log.info("Creando usuario: {}", usuarioDTO.getUsuario());
        return mapper.usuariosEntityToUsuariosDTO(repo.save(usuario));
    }
    @Override
    public UsuarioDTO actualizar(long id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> optionalUsuario = repo.findById(id);
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
            return mapper.usuariosEntityToUsuariosDTO(repo.save(usuario));
        } else {
            throw new RuntimeException("Usuario con id " + id + " no existe");
        }
    }

    @Override
    public Optional<UsuarioDTO> eliminar(long id) {
        Optional<Usuario> usuarioAEliminar = repo.findById(id);
        if (usuarioAEliminar.isPresent()) {
            Usuario usuarioEliminado = usuarioAEliminar.get();
            repo.delete(usuarioEliminado);
            return Optional.of(mapper.usuariosEntityToUsuariosDTO(usuarioEliminado));
        } else {
            throw new NotFoundException(Usuario.class, String.valueOf(id));
        }
    }
    @Override
    public UsuarioDTO buscarUsuario(String usuarioOmail) {
        return mapper.usuariosEntityToUsuariosDTO(repo.findByUsuarioOrMail(usuarioOmail, usuarioOmail).get());
    }

}
