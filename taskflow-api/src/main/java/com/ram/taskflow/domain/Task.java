package com.ram.taskflow.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {
  public enum Status { TODO, IN_PROGRESS, DONE }

  @Id
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(name = "project_id", nullable = false)
  private UUID projectId;

  @Column(nullable = false, length = 180)
  private String title;

  @Column(length = 1200)
  private String details;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private Status status;

  @Column(name = "due_on")
  private LocalDate dueOn;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  protected Task() {}

  public Task(UUID id, UUID userId, UUID projectId, String title, String details, Status status, LocalDate dueOn, Instant createdAt) {
    this.id = id;
    this.userId = userId;
    this.projectId = projectId;
    this.title = title;
    this.details = details;
    this.status = status;
    this.dueOn = dueOn;
    this.createdAt = createdAt;
  }

  public UUID getId() { return id; }
  public UUID getUserId() { return userId; }
  public UUID getProjectId() { return projectId; }
  public String getTitle() { return title; }
  public String getDetails() { return details; }
  public Status getStatus() { return status; }
  public LocalDate getDueOn() { return dueOn; }
  public Instant getCreatedAt() { return createdAt; }

  public void setStatus(Status status) { this.status = status; }
}
