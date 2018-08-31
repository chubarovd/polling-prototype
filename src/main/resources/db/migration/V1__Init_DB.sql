create sequence hibernate_sequence start 1 increment 1;

create table items (
  id int8 not null,
  content varchar(2048) not null,
  primary key (id)
);

create table oldvotes (
  id int8 not null,
  content varchar(2048) not null,
  count int4,
  user_id int8 not null,
  primary key (id)
);

create table user_role (
  user_id int8 not null,
  roles varchar(255)
);

create table users (
  id int8 not null,
  active boolean,
  last_poll_time date,
  password varchar(255) not null,
  username varchar(255) not null,
  votes_limit int4 not null,
  primary key (id)
);

create table votes (
  id int8 not null,
  count int4,
  user_id int8 not null,
  item_id int8 not null,
  primary key (id)
);

alter table if exists oldvotes
  add constraint oldvote_user_kf
  foreign key (user_id) references users;

alter table if exists user_role
  add constraint user_role_user_fk
  foreign key (user_id) references users;

alter table if exists votes
  add constraint vote_user_fk
  foreign key (user_id) references users;

alter table if exists votes
  add constraint vote_item_fk
  foreign key (item_id) references items;
