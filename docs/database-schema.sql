create table aircraft (
    id bigserial primary key,
    model varchar(80) not null,
    tail_number varchar(20) not null unique,
    seats integer not null check (seats > 0)
);

create table airports (
    id bigserial primary key,
    code varchar(10) not null unique,
    name varchar(120) not null,
    city varchar(80) not null,
    country varchar(80) not null
);

create table passengers (
    id bigserial primary key,
    first_name varchar(80) not null,
    last_name varchar(80) not null,
    email varchar(160) not null unique,
    document_number varchar(40) not null unique
);

create table flights (
    id bigserial primary key,
    flight_number varchar(20) not null unique,
    departure_airport_id bigint not null references airports(id),
    arrival_airport_id bigint not null references airports(id),
    departure_time timestamp not null,
    aircraft_id bigint not null references aircraft(id),
    status varchar(30) not null
);

create table bookings (
    id bigserial primary key,
    passenger_id bigint not null references passengers(id),
    flight_id bigint not null references flights(id),
    seat_number varchar(10) not null,
    status varchar(30) not null
);
