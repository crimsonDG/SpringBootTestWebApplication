insert into users(id, login, email, password) values (1, 'SpitFire', 'spitfire@gmail.com', '$2a$04$.NsXsT4omiHMlvgJqUqAg.pjq61qRK14da.9IXi0yRULwmdtl71Wq');

insert into users(id, login, email, password) values (2, 'Claw', 'motorclaw@somemail.com', '$2a$04$PCKSxFh.gGTzus2NHjMye.78D96rAysIV.ntp5MoSvGztMI91qJNK');

insert into users(id, login, email, password) values (3, 'Bomber', 'stoneforever@mmail.uk', '$2a$04$Qk8KFO0qgcjUQBsRXcYWj.wyvOQ0kBuWrvV0miZM1QHqcBOpX0X4e');

SELECT setval(pg_get_serial_sequence('users', 'id'), coalesce(max(id)+1, 1), false) FROM users;