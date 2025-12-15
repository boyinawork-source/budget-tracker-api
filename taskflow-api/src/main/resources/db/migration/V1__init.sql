create table users (
  id uuid primary key,
  email varchar(255) not null unique,
  password_hash varchar(255) not null,
  created_at timestamptz not null
);

create table projects (
  id uuid primary key,
  user_id uuid not null references users(id),
  name varchar(140) not null,
  description varchar(600),
  created_at timestamptz not null,
  unique(user_id, name)
);

create table tasks (
  id uuid primary key,
  user_id uuid not null references users(id),
  project_id uuid not null references projects(id),
  title varchar(180) not null,
  details varchar(1200),
  status varchar(30) not null,
  due_on date,
  created_at timestamptz not null
);

create index idx_tasks_user_project on tasks(user_id, project_id);
create index idx_tasks_user_status on tasks(user_id, status);
create index idx_tasks_user_due on tasks(user_id, due_on);
