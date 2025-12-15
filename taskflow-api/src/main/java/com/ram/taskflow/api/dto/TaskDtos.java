package com.ram.taskflow.api.dto;

import com.ram.taskflow.domain.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public final class TaskDtos {
  private TaskDtos() {}

  public record CreateTaskRequest(
      @NotNull UUID projectId,
      @NotBlank @Size(max = 180) String title,
      @Size(max = 1200) String details,
      LocalDate dueOn
  ) {}

  public record TaskResponse(
      UUID id,
      UUID projectId,
      String title,
      String details,
      Task.Status status,
      LocalDate dueOn
  ) {}

  public record UpdateStatusRequest(@NotNull Task.Status status) {}
}
