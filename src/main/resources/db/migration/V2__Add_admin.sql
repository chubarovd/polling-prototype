insert into user_table (id, username, password, active, votes_limit, last_poll_time)
  values (1, 'admin', 'admin', true, 999, current_date);

insert into user_role (user_id, roles)
  values (1, 'ADMIN');