create table addresses (
    id bigint generated always as identity,
    city varchar(255) not null,
    complement varchar(255) not null,
    created_at timestamp(6) not null,
    deleted boolean not null,
    deleted_at timestamp(6),
    neighborhood varchar(255) not null,
    number integer not null,
    street varchar(255) not null,
    updated_at timestamp(6),
    zip_code varchar(255) not null,
    primary key (id)
);

create table otps (
    id bigint generated always as identity,
    code varchar(6) not null,
    created_at timestamp(6) not null,
    deleted boolean not null,
    deleted_at timestamp(6),
    user_id bigint,
    primary key (id)
);

create table users (
    id bigint generated always as identity,
    confirmed boolean not null,
    created_at timestamp(6) not null,
    deleted boolean not null,
    deleted_at timestamp(6),
    email varchar(255) not null,
    password varchar(255) not null,
    role varchar(255) check (role in ('USER','CLIENT','SELLER','SELLER_CLIENT','ADMIN')),
    updated_at timestamp(6),
    name varchar(255),
    address_id bigint,
    primary key (id)
);

alter table if exists otps
    add constraint FK_Otp_User foreign key (user_id) references users;

alter table if exists users
    add constraint UK_User_Name unique (name);

alter table if exists users
    add constraint UK_User_Addresses unique (address_id);

alter table if exists users
    add constraint FK_User_Addresses foreign key (address_id) references addresses;