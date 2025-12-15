package com.ram.taskflow.service;

import com.ram.taskflow.domain.User;
import com.ram.taskflow.error.BadRequestException;
import com.ram.taskflow.repo.UserRepository;
import com.ram.taskflow.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {
  private final UserRepository users;
  private final JwtService jwt;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public AuthService(UserRepository users, JwtService jwt) {
    this.users = users;
    this.jwt = jwt;
  }

  public String register(String email, String password) {
    String normalized = email.toLowerCase().trim();
    if (users.existsByEmail(normalized)) throw new BadRequestException("Email already registered");
    User u = new User(UUID.randomUUID(), normalized, encoder.encode(password), Instant.now());
    users.save(u);
    return jwt.issue(u.getId(), u.getEmail());
  }

  public String login(String email, String password) {
    String normalized = email.toLowerCase().trim();
    User u = users.findByEmail(normalized).orElseThrow(() -> new BadRequestException("Invalid credentials"));
    if (!encoder.matches(password, u.getPasswordHash())) throw new BadRequestException("Invalid credentials");
    return jwt.issue(u.getId(), u.getEmail());
  }
}
