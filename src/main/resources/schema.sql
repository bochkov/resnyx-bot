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
    val       varchar(3),
    region_id int,
    foreign key (region_id) references region (id)
);

drop table if exists currency_item;
drop table if exists currency_rate;
drop table if exists currency_rate_record;

create table currency_item
(
    id            varchar(10) primary key,
    name          varchar(100),
    eng_name      varchar(100),
    nominal       int,
    parent_code   varchar(10),
    iso_num_code  int,
    iso_char_code varchar(3)
);

create table currency_rate
(
    id     bigint primary key auto_increment,
    `date` date,
    fetch_time timestamp not null default CURRENT_TIMESTAMP(),
    `name` varchar(100)
);

create table currency_rate_record
(
    id         varchar(10) primary key,
    rate_id    bigint,
    num_code   varchar(3),
    char_code  varchar(3),
    nominal    int,
    `name`     varchar(100),
    rate_value numeric(10, 4),
    foreign key (rate_id) references currency_rate (id)
);