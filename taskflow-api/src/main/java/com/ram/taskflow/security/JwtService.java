package com.ram.taskflow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
  private final String secret;
  private final long minutes;

  public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.minutes}") long minutes) {
    this.secret = secret;
    this.minutes = minutes;
  }

  public String issue(UUID userId, String email) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    Instant now = Instant.now();
    return Jwts.builder()
        .subject(userId.toString())
        .claim("email", email)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(minutes * 60)))
        .signWith(key)
        .compact();
  }

  public Claims parse(String token) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }
}
