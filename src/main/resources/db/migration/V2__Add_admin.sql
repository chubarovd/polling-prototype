insert into users (id, username, password, active, votes_limit, last_poll_time)
  values (0, 'admin', 'admin', true, 999, null);

insert into user_role (user_id, roles)
  values (0, 'ADMIN');