package com.asj.listed.security;

import com.asj.listed.exceptions.UnauthorizedException;
import com.asj.listed.security.services.JwtTokenService;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, UnauthorizedException {
        try {
            String jwt = request.getHeader("Authorization");
            if (jwt == null || !jwt.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
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
                throw new UnauthorizedException("Token de autorización inválido");
            }
        } catch (UnauthorizedException e) {
            log.error("Invalid authentication token", e);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
            return;
        } catch (Exception e){
            log.error("Error on authentication process, e");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }
}