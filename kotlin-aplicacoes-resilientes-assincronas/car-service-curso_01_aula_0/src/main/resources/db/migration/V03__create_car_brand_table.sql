create table if not exists car_brand(
    id bigint not null auto_increment,
    name varchar(50) not null,
    code bigint not null,
    primary key(id)
);
