package com.asj.listed.security.services;

import com.asj.listed.model.entities.Usuario;
import com.asj.listed.repositories.UsuariosRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuariosRepository repo;
    public UserDetailsServiceImpl(UsuariosRepository repo) {
        this.repo = repo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = repo.findByUsuarioOrMail(username, username);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            GrantedAuthority roles = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());
            return new User(usuario.getUsuario(), usuario.getPassword(), Collections.singletonList(roles));
        }
        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
