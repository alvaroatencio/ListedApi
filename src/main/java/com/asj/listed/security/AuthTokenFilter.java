package com.asj.listed.security;

import com.asj.listed.exceptions.UnauthorizedException;
import com.asj.listed.model.response.GenericResponse;
import com.asj.listed.security.services.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("AuthTokenFilter.doFilterInternal entra");
        response.setHeader("Content-Type", "application/json");
        ObjectMapper objectMapper = new ObjectMapper();

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                String username = jwtTokenService.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("AuthTokenFilter.doFilterInternal fin del try");
            } catch (BadCredentialsException e) {
                throw new UnauthorizedException("Token inválido o expirado");
            } catch (UnauthorizedException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                String responseBody = objectMapper.writeValueAsString(new GenericResponse(
                        false,
                        "Invalid authentication token",
                        e.getMessage()
                ));
                response.getWriter().write(responseBody);
                return;
            } catch (Exception e) {
                throw new UnauthorizedException(e.getMessage());
            }
        }
        System.out.println("AuthTokenFilter.doFilterInternal ultimo return");
        filterChain.doFilter(request, response);
    }
}