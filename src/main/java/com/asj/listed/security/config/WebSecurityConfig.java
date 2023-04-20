package com.asj.listed.security.config;

import com.asj.listed.security.AuthTokenFilter;
import com.asj.listed.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class WebSecurityConfig implements WebMvcConfigurer {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    // Se podría cambiar por DaoAuthenticationProvider y utilizar el método setSaltSource()
    // para configurar el origen de la sal para la encriptación de contraseñas,
    // o por LdapAuthenticationProvider para autenticar a los usuarios contra un servidor LDAP en lugar de una db.
    //En general, se recomienda usar el método con tipo de retorno AuthenticationProvider,
    // a menos que se necesite acceder a los métodos específicos de las otras clases.
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests((auth) -> auth
                //.anyRequest().authenticated()
                .anyRequest().permitAll());

        http.httpBasic(withDefaults());

        return http.build();
    }
    //// TODO: 20/4/2023  no esta funcionando este método:
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/auth/login")
                .requestMatchers(HttpMethod.POST,"/usuarios")
                .requestMatchers(HttpMethod.POST,"/titulares")
                .requestMatchers(HttpMethod.POST,"/cuentas");
        /*return (web) -> web.ignoring()
                .requestMatchers(HttpMethod.POST, "/login")
                .requestMatchers(HttpMethod.POST, "/usuarios")
                //BORRAR PUT Y DELETE, DEBEN ESTAR BLOQUEADOS
                .requestMatchers(HttpMethod.PUT, "/usuarios")
                .requestMatchers(HttpMethod.DELETE, "/usuarios")
                .requestMatchers(HttpMethod.POST, "/titulares")
                .requestMatchers(HttpMethod.POST, "/cuentas");
         */
    }

    @Bean
    public SecurityContextHolder securityContextHolder() {
        return new SecurityContextHolder();
    }

    //HAY 2 FORMAS DE LIMPIAR EL CORS:
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("Authorization");
    }
}