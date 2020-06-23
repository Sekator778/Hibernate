drop table if exists engines cascade ;
drop table if exists cars cascade ;

create table if not exists engines
(
    id serial primary key,
    model varchar(90),
    power int
);

create table if not exists cars
(
    id        serial primary key,
    mark varchar(90),
    model varchar(90),
    engine_id int not null unique references engines (id) not null
);
