package com.asj.listed.security.services;

import com.asj.listed.security.entities.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
@Service
public class JwtService {
    //@Value("${jwt.secret}")
    private String jwtSecret="6666888877779999SKKFDSKJFKSDJGKBNRJaaaaaassds";
    //@Value("${jwt.expiration}")
    private int jwtExpiration=600000000;

    public String crearToken(Authentication authentication){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Date expirationDate = new Date(System.currentTimeMillis() + jwtExpiration);

            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty");
        }
        return false;
    }
    public String getUserNameFromToken(String token) {
        if(validateToken(token)){
            return extractClaim(token, Claims::getSubject);
        }
        //// TODO: 26/3/2023  borrar esto y mejorar
        System.out.println("JwtService.getUserNameFromToken: algo salio mal");
        return "false";
    }
    private Date getExpirationFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
    }
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
