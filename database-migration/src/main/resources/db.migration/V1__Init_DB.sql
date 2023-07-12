create table user_entity (
    id varchar(255) not null,
    email varchar(255),
    email_constraint varchar(255),
    email_verified boolean not null,
    enabled boolean not null,
    federation_link varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    realm_id varchar(255) not null,
    username varchar(255) not null,
    created_timestamp bigint not null,
    service_account_client_link varchar(255),
    not_before integer not null,
    primary key (id)
);

create table credential (
    id varchar(255) not null,
    salt bytea,
    type varchar(255) not null,
    user_id varchar(255) not null,
    created_date bigint not null,
    user_label varchar(255),
    secret_data text not null,
    credential_data text not null,
    priority integer not null,
    primary key (id),
    constraint fk_user
      foreign key(user_id)
	    references user_entity(id)
);

create table user_role_mapping (
    role_id varchar(255) not null,
    user_id varchar(255) not null,
    primary key (role_id, user_id)
);

alter table if exists user_role_mapping
    add constraint role_fk
    foreign key (id) references keycloak_role;

alter table if exists user_role_mapping
    add constraint user_fk
    foreign key (id) references user_entity;