create sequence hibernate_sequence start 1 increment 1;

create table item_table (
  id int8 not null,
  content varchar(2048) not null,
  primary key (id)
);

create table user_role (
  user_id int8 not null,
  roles varchar(255)
);

create table user_table (
  id int8 not null,
  active boolean,
  last_poll_time date not null,
  password varchar(255) not null,
  username varchar(255) not null,
  votes_limit int4,
  primary key (id)
);

create table votes_table (
  id int8 not null,
  count int4,
  user_id int8 not null,
  item_id int8 not null,
  primary key (id)
);

alter table if exists user_role
  add constraint user_role_user_fk
  foreign key (user_id) references user_table;

alter table if exists votes_table
  add constraint vote_user_fk
  foreign key (user_id) references user_table;

alter table if exists votes_table
  add constraint vote_item_fk
  foreign key (item_id) references item_table;
