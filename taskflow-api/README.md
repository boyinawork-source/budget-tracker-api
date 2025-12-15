# TaskFlow API (Java 17 / Spring Boot 3)

Backend project with JWT auth, Projects, Tasks, filtering, and PostgreSQL persistence.

## Run
docker compose up --build

## Swagger
http://localhost:8080/swagger-ui.html

## Auth
POST /api/auth/register
POST /api/auth/login
Authorization: Bearer <token>

## Projects
POST /api/projects
GET  /api/projects
GET  /api/projects/{id}
DELETE /api/projects/{id}

## Tasks
POST /api/tasks
GET  /api/tasks?projectId=&status=&dueBefore=
PATCH /api/tasks/{id}/status
DELETE /api/tasks/{id}
