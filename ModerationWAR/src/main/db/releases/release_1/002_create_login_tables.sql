DROP TABLE IF EXISTS users;

GO

create table users (
  user_name varchar(20) not null primary key,
  user_pass varchar(20) not null
);

GO

DROP TABLE IF EXISTS user_roles;

GO

create table user_roles (
  user_name varchar(20) not null,
  role_name varchar(20) not null,
  primary key (user_name, role_name)
);

GO