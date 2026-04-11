package com.library.LibraryManagement.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.accessTokenSecret}")
    private String accessTokenSecret;

    @Value("${jwt.refreshTokenSecret}")
    private String refreshTokenSecret;

    @Value("${jwt.accessTokenExpiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refreshTokenExpiration}")
    private long refreshTokenExpiration;

    private SecretKey accessKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecret));
    }

    private SecretKey refreshKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenSecret));
    }

    public String generateAccessToken(String username, String role) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + accessTokenExpiration))
                .signWith(accessKey())
                .compact();
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + refreshTokenExpiration))
                .signWith(refreshKey())
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().verifyWith(accessKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().verifyWith(refreshKey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromAccessToken(String token) {
        return parseClaims(token, accessKey()).getSubject();
    }

    public String getUsernameFromRefreshToken(String token) {
        return parseClaims(token, refreshKey()).getSubject();
    }

    public String getRoleFromAccessToken(String token) {
        return parseClaims(token, accessKey()).get("role", String.class);
    }

    private Claims parseClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Kept for backward compatibility during migration
    @Deprecated
    public String generateToken(String data) {
        return generateAccessToken(data, "");
    }

    @Deprecated
    public boolean verifyToken(String token) {
        return validateAccessToken(token);
    }

    @Deprecated
    public String getUsernameFromToken(String token) {
        return getUsernameFromAccessToken(token);
    }
}
