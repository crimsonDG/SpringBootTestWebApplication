create table label (
    id varchar(255) not null,
    name varchar(255) not null,
    songs text[],
    primary key (id)
);