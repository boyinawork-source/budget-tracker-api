package com.ram.taskflow.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class JwtAuthFilter extends OncePerRequestFilter {
  private final JwtService jwt;

  public JwtAuthFilter(JwtService jwt) {
    this.jwt = jwt;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, jakarta.servlet.ServletException {

    String path = request.getRequestURI();
    if (path.startsWith("/api/auth") || path.startsWith("/swagger") || path.startsWith("/v3/api-docs") || path.startsWith("/actuator")) {
      chain.doFilter(request, response);
      return;
    }

    String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (auth == null || !auth.startsWith("Bearer ")) {
      response.setStatus(401);
      return;
    }

    try {
      Claims c = jwt.parse(auth.substring(7));
      UUID userId = UUID.fromString(c.getSubject());
      String email = String.valueOf(c.get("email"));

      var principal = new Principal(userId, email);
      var token = new UsernamePasswordAuthenticationToken(principal, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
      SecurityContextHolder.getContext().setAuthentication(token);

      chain.doFilter(request, response);
    } catch (Exception e) {
      response.setStatus(401);
    }
  }

  public record Principal(UUID userId, String email) {}
}
