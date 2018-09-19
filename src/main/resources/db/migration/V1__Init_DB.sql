create sequence hibernate_sequence start 1 increment 1;

create table categories (
  id int8 not null,
  title varchar(255),
  primary key (id)
);

create table feature_category (
  feature_id int8 not null,
  category_id int8 not null,
  primary key (
    feature_id,
    category_id
  )
);

create table features (
  id int8 not null,
  description varchar(255),
  stars_count int4 not null,
  status varchar(255),
  title varchar(255),
  voted boolean not null,
  votes_count int4 not null,
  primary key (id)
);

create table items (
  id int8 not null,
  content varchar(255),
  primary key (id)
);

create table oldvotes (
  id int8 not null,
  content varchar(255),
  count int4,
  user_id int8,
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
  password varchar(255),
  username varchar(255),
  votes_limit int4, primary key (id)
);

create table votes (
  id int8 not null,
  count int4,
  user_id int8,
  item_id int8,
  primary key (id)
);

alter table if exists feature_category
  add constraint category_fk
  foreign key (category_id) references categories;

alter table if exists feature_category
  add constraint feature_fk
  foreign key (feature_id) references features;

alter table if exists oldvotes
  add constraint user_oldvote_fk
  foreign key (user_id) references users;

alter table if exists user_role
  add constraint user_role_fk
  foreign key (user_id) references users;

alter table if exists votes
  add constraint user_vote_fk
  foreign key (user_id) references users;

alter table if exists votes
  add constraint item_vote_fk
  foreign key (item_id) references items;