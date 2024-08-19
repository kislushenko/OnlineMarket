package com.online.market.gatewayservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtils {
    public static final String BEARER_PREFIX = "Bearer ";

    private SecretKey key;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getClaims(String token) {
        if (StringUtils.isNotBlank(token)) {
            String jwtToken = token.replace(BEARER_PREFIX, "");
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken).getPayload();
        }
        throw new IllegalArgumentException("Token is empty");
    }

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }


}
