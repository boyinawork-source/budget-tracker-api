package com.ram.taskflow.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class Project {
  @Id
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @Column(nullable = false, length = 140)
  private String name;

  @Column(length = 600)
  private String description;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  protected Project() {}

  public Project(UUID id, UUID userId, String name, String description, Instant createdAt) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.description = description;
    this.createdAt = createdAt;
  }

  public UUID getId() { return id; }
  public UUID getUserId() { return userId; }
  public String getName() { return name; }
  public String getDescription() { return description; }
  public Instant getCreatedAt() { return createdAt; }
}
