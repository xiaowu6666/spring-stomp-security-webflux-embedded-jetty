CREATE TABLE users (
  id         INTEGER PRIMARY KEY,
  username VARCHAR(30) not null,
  email  VARCHAR(50) not null,
  password VARCHAR(100) not null,
  avatar VARCHAR(100) null,
  enable boolean
);

CREATE TABLE authorities(
  id  INTEGER PRIMARY KEY,
  email varchar(50) not null,
  authority varchar(50) not null
)