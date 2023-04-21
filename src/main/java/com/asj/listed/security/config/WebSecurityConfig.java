package com.asj.listed.security.config;

import com.asj.listed.security.AuthTokenFilter;
import com.asj.listed.security.exceptions.handler.AuthorizationHandler;
import com.asj.listed.security.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class WebSecurityConfig implements WebMvcConfigurer {
    private final AuthTokenFilter authTokenFilter;
    private final UserDetailsServiceImpl userDetailsService;

    //Esta es una forma de declarar el servicio de usuarios si trabajáramos entidades del mismo tipo
    /*
    private final UserRepository userRepository;
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> (UserDetails) userRepository.findByUsuarioOrMail(username,username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }*/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //CORS Y SESSION
        http
                .cors(Customizer.withDefaults())
                //.csrf().disable() //esta también es una opción
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //FILTROS
        http
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults());
        //CONFIGURACIÓN DE ENDPOINTS
        //dato importante, el orden si importa cuando estamos configurando los métodos de un mismo controller
        http
                .authorizeHttpRequests()
                .requestMatchers("/auth/login").permitAll()
                //si existe una regla general igual se debe agregar el rol correspondiente, en este caso ADMIN
                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("USUARIO","ADMIN")
                .requestMatchers(HttpMethod.PUT).permitAll()
                .requestMatchers(HttpMethod.DELETE).hasAnyRole("ADMIN", "USUARIO")
                //la regla general debe ir luego de las específicas
                .requestMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
                .requestMatchers("/test/usuario").hasRole("USUARIO")
                .requestMatchers("/test/admin").hasRole("ADMIN")
                .requestMatchers("/test/bloqueado").hasRole("BLOQUEADO")
        ;
        //CONFIGURACIÓN DE HANDLER DE EXCEPCIONES
        http.exceptionHandling().accessDeniedHandler(new AuthorizationHandler());
        return http.build();
    }
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