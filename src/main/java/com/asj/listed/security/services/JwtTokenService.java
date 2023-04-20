package com.asj.listed.security.services;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.function.Function;
@Service
@Component
public class JwtTokenService{
    private String jwtSecret="2948404D635166546A576E5A7234753778214125432A462D4A614E645267556B";
    private int jwtExpiration=60000;

    public String createToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpiration);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
    public boolean isTokenValid(String token) {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
    }
    public String getUsernameFromToken(String token) throws AuthenticationException {
        if (isTokenValid(token)) {
            return extractClaim(token, Claims::getSubject);
        }
        throw new BadCredentialsException("Token inválido o expirado");
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
    }
}