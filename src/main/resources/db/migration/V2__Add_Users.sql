insert into users(user_id, login, email, password)
    values (1, 'SpitFire', 'spitfire@gmail.com', '$2a$04$.NsXsT4omiHMlvgJqUqAg.pjq61qRK14da.9IXi0yRULwmdtl71Wq');

insert into users(user_id, login, email, password)
    values (2, 'Claw', 'motorclaw@somemail.com', '$2a$04$PCKSxFh.gGTzus2NHjMye.78D96rAysIV.ntp5MoSvGztMI91qJNK');

insert into users(user_id, login, email, password)
    values (3, 'Admin', 'stoneforever@mmail.uk', '$2a$04$Qk8KFO0qgcjUQBsRXcYWj.wyvOQ0kBuWrvV0miZM1QHqcBOpX0X4e');

SELECT setval(pg_get_serial_sequence('users', 'user_id'), coalesce(max(user_id)+1, 1), false) FROM users;

insert into roles(role_id, name)
    values (1, 'USER');

insert into roles(role_id, name)
    values (2, 'ADMIN');

insert into users_roles(user_id, role_id)
    values (1, 1);

insert into users_roles(user_id, role_id)
    values (2, 1);

insert into users_roles(user_id, role_id)
    values (3, 2);