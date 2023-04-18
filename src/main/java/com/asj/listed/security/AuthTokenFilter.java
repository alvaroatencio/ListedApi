package com.asj.listed.security;

import com.asj.listed.exceptions.UnauthorizedException;
import com.asj.listed.model.response.GenericResponse;
import com.asj.listed.security.services.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, UnauthorizedException {;
        response.setHeader("Content-Type", "application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jwt = request.getHeader("Authorization");
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                throw new UnauthorizedException("Token de autorización inválido");
            }
            jwt = jwt.substring(7);
            if (jwtService.isTokenValid(jwt)) {
                String username = jwtService.getUsernameFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                String responseBody = objectMapper.writeValueAsString(new GenericResponse(
                        false,
                        "Invalid authentication token",
                        "Token de autorización inválido"
                ));
                response.getWriter().write(responseBody);
                return;
            }
        } catch (UnauthorizedException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String responseBody = objectMapper.writeValueAsString(new GenericResponse(
                    false,
                    "Invalid authentication token",
                    e.getMessage()
            ));
            response.getWriter().write(responseBody);
            return;
        } catch (Exception e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String responseBody = objectMapper.writeValueAsString(new GenericResponse(
                    false,
                    "Process error",
                    e.getMessage()
            ));
            response.getWriter().write(responseBody);
            return;
        }
        filterChain.doFilter(request, response);
    }
}