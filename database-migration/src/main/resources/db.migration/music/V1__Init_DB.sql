create table songs (
    id varchar(255) not null,
    name varchar(255) not null,
    artist varchar(255) not null,
    album varchar(255),
    year integer not null,
    duration varchar(255) not null,
    primary key (id)
);