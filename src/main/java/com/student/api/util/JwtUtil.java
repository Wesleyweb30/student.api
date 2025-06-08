package com.student.api.util;

import java.security.Key;
import java.util.Date;

import org.springframework.context.annotation.Configuration;

import com.student.api.model.UserType;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtUtil {
    // Use uma chave segura REAL em produção!
    private static final String SECRET_KEY = "minha-chave-super-secreta-de-mais-de-32-caracteres";
    private static final long EXPIRATION_MS = 1000 * 60 * 60 * 6; // 6 horas

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String gerarToken(String email, UserType tipo) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", tipo.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
