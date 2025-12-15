package com.ram.taskflow.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public final class AuthDtos {
  private AuthDtos() {}

  public record RegisterRequest(@Email @NotBlank String email, @NotBlank String password) {}
  public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
  public record TokenResponse(String token) {}
}
