package com.ram.taskflow.api;

import com.ram.taskflow.api.dto.TaskDtos;
import com.ram.taskflow.domain.Task;
import com.ram.taskflow.security.JwtAuthFilter.Principal;
import com.ram.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
  private final TaskService tasks;

  public TaskController(TaskService tasks) {
    this.tasks = tasks;
  }

  @PostMapping
  public TaskDtos.TaskResponse create(Authentication auth, @Valid @RequestBody TaskDtos.CreateTaskRequest req) {
    Principal p = (Principal) auth.getPrincipal();
    Task t = tasks.create(p.userId(), req.projectId(), req.title(), req.details(), req.dueOn());
    return new TaskDtos.TaskResponse(t.getId(), t.getProjectId(), t.getTitle(), t.getDetails(), t.getStatus(), t.getDueOn());
  }

  @GetMapping
  public List<TaskDtos.TaskResponse> search(
      Authentication auth,
      @RequestParam(required = false) UUID projectId,
      @RequestParam(required = false) Task.Status status,
      @RequestParam(required = false) LocalDate dueBefore
  ) {
    Principal p = (Principal) auth.getPrincipal();
    return tasks.search(p.userId(), projectId, status, dueBefore).stream()
        .map(t -> new TaskDtos.TaskResponse(t.getId(), t.getProjectId(), t.getTitle(), t.getDetails(), t.getStatus(), t.getDueOn()))
        .toList();
  }

  @PatchMapping("/{id}/status")
  public TaskDtos.TaskResponse status(Authentication auth, @PathVariable UUID id, @Valid @RequestBody TaskDtos.UpdateStatusRequest req) {
    Principal p = (Principal) auth.getPrincipal();
    Task t = tasks.updateStatus(p.userId(), id, req.status());
    return new TaskDtos.TaskResponse(t.getId(), t.getProjectId(), t.getTitle(), t.getDetails(), t.getStatus(), t.getDueOn());
  }

  @DeleteMapping("/{id}")
  public void delete(Authentication auth, @PathVariable UUID id) {
    Principal p = (Principal) auth.getPrincipal();
    tasks.delete(p.userId(), id);
  }
}
