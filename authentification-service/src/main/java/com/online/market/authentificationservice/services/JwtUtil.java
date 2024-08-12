package com.online.market.authentificationservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
@PropertySource("classpath:application.yml")
public class JwtUtil {

    @Value("${jwt.expiration}")
    private String expiration;

    private SecretKey key;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    private boolean isExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    public String generate(String userId, String role, String tokenType) {
        Map<String, String> claimns = Map.of(
                "id", userId,
                "role", role);
        return buildToken(claimns, tokenType);
    }

    private String buildToken(Map<String, String> claims, String tokenType) {
        long expMillis = "ACCESS".equalsIgnoreCase(tokenType)
                ? Long.parseLong(expiration) * 1000
                : Long.parseLong(expiration) * 5000;

        final Date now = new Date();
        final Date exp = new Date(now.getTime() + expMillis);
        return Jwts.builder()
                .claims(claims)
                .subject(claims.get("id"))
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }

}
