package com.ram.taskflow.api;

import com.ram.taskflow.api.dto.AuthDtos;
import com.ram.taskflow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService auth;

  public AuthController(AuthService auth) {
    this.auth = auth;
  }

  @PostMapping("/register")
  public AuthDtos.TokenResponse register(@Valid @RequestBody AuthDtos.RegisterRequest req) {
    return new AuthDtos.TokenResponse(auth.register(req.email(), req.password()));
  }

  @PostMapping("/login")
  public AuthDtos.TokenResponse login(@Valid @RequestBody AuthDtos.LoginRequest req) {
    return new AuthDtos.TokenResponse(auth.login(req.email(), req.password()));
  }
}
