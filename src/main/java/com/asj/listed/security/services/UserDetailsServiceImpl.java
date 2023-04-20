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

    //todo Esta implementacion es necesaria debido a que mi repositorio trabaja con entidades User y necesito convertirlas a UserDetails
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Optional<User> usuarioOptional = repo.findByUsuarioOrMail(username, username);
        if (usuarioOptional.isPresent()) {
            User user = usuarioOptional.get();
            GrantedAuthority rol = new SimpleGrantedAuthority("ROLE_" + user.getRol());
            System.out.println("UserDetailsServiceImpl.loadUserByUsername \n"+user+"\n"+new org.springframework.security.core.userdetails.User(
                    user.getUsuario(),
                    user.getPassword(),
                    Collections.singletonList(rol)));
            return new org.springframework.security.core.userdetails.User(
                    user.getUsuario(),
                    user.getPassword(),
                    Collections.singletonList(rol));
        }
        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
