package com.ram.taskflow.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public final class ProjectDtos {
  private ProjectDtos() {}

  public record CreateProjectRequest(@NotBlank @Size(max = 140) String name, @Size(max = 600) String description) {}
  public record ProjectResponse(UUID id, String name, String description) {}
}
