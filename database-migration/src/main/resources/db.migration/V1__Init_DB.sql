create table users (
    user_id bigserial not null,
    email varchar(255) not null,
    login varchar(255) not null,
    password varchar(255) not null,
    primary key (user_id)
);

create table roles (
    role_id serial not null,
    name varchar(255) not null,
    primary key (role_id)
);

create table users_roles (
    user_id bigint not null,
    role_id integer not null,
    primary key (user_id, role_id)
);

alter table if exists users_roles
    add constraint role_fk
    foreign key (role_id) references roles;

alter table if exists users_roles
    add constraint user_fk
    foreign key (user_id) references users;