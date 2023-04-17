package com.asj.listed.security.services;

import com.asj.listed.model.entities.User;
import com.asj.listed.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repo;
    public UserDetailsServiceImpl(UserRepository repo) {
        this.repo = repo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usuarioOptional = repo.findByUsuarioOrMail(username, username);
        if (usuarioOptional.isPresent()) {
            User user = usuarioOptional.get();
            GrantedAuthority roles = new SimpleGrantedAuthority("ROLE_" + user.getRol());
            return new org.springframework.security.core.userdetails.User(
                    user.getUsuario(), user.getPassword(), Collections.singletonList(roles));
        }
        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
