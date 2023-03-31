package com.asj.listed.security.services;

import com.asj.listed.repositories.UsuariosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UsuariosRepository repo;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtUtils;

    public AuthService(UsuariosRepository repo, PasswordEncoder passwordEncoder, JwtService jwtUtils) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    /*public String authenticate(LoginRequestDTO loginRequestDTO) throws AuthenticationException {
        Optional<Usuarios> usuario = repo.findByUsuarioOrMail(loginRequestDTO.getUsuario(), loginRequestDTO.getUsuario());
        if (usuario.isPresent()) {
            boolean valido = passwordEncoder.matches(loginRequestDTO.getPassword(), usuario.get().getPassword());
            if (!valido) {
                throw new RuntimeException("Usuario o contraseña incorrectos");
            }
            return jwtUtils.generateToken((UserDetails) usuario.get());
        }
        throw new NotFoundException("Usuario no encontrado");
    }*/
}