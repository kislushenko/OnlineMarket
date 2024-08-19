package com.online.market.orderservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@PropertySource("classpath:application.yml")
public class JwtUtil {
    public static final String BEARER_PREFIX = "Bearer ";

    private SecretKey key;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractEmail(final String token) {
        return getClaims(token).get("id", String.class);
    }

    private Claims getClaims(String token) {
        if (StringUtils.isNotBlank(token)) {
            String jwtToken = token.replace(BEARER_PREFIX, "");
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
        }
        throw new IllegalArgumentException("Token is empty");
    }

}
