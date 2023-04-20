package com.asj.listed.security.config;

import com.asj.listed.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //Esta es una forma de declarar el sevicio de usuarios si trabajaramos entidades del mismo tipo
    /*
    private final UserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userRepository.findByUsuarioOrMail(username,username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }*/





}