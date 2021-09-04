drop table if exists code;
drop table if exists region;

create table region
(
    id   int primary key auto_increment,
    name varchar(255)
);

create table code
(
    id        int primary key auto_increment,
    value     varchar(3),
    region_id int,
    foreign key (region_id) references region (id)
);