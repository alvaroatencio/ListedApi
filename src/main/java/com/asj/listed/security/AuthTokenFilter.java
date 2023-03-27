package com.asj.listed.security;

import com.asj.listed.security.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    public AuthTokenFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    public AuthTokenFilter() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = request.getHeader("Authorization");
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                System.out.println("AuthTokenFilter.doFilterInternal: jwt null o sin Bearer");
                filterChain.doFilter(request, response);
                return;
            }
            System.out.println("AuthTokenFilter.doFilterInternal: pasa");
            jwt = jwt.substring(7);
            String username = jwtService.getUserNameFromToken(jwt);
            System.out.println("AuthTokenFilter.doFilterInternal: veamos si esta aca");
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("AuthTokenFilter.doFilterInternal userDetails: "+userDetails);
            if (jwtService.validateToken(jwt)) {
                System.out.println("AuthTokenFilter.doFilterInternal: entra a segundo if");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            //// TODO: 26/3/2023  borrar esto y reemplazar por algo util
            System.out.println("AuthTokenFilter.doFilterInternal Error: " + e);
        }
        System.out.println("AuthTokenFilter.doFilterInternal: sale");
        filterChain.doFilter(request, response);
    }
}