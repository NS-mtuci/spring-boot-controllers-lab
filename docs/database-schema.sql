create table aircraft (
    id bigserial primary key,
    model varchar(80) not null,
    tail_number varchar(20) not null unique,
    seats integer not null check (seats > 0)
);

create table flights (
    id bigserial primary key,
    flight_number varchar(20) not null unique,
    departure_city varchar(80) not null,
    arrival_city varchar(80) not null,
    departure_time timestamp not null,
    aircraft_id bigint not null references aircraft(id),
    status varchar(30) not null
);

create table bookings (
    id bigserial primary key,
    passenger_name varchar(120) not null,
    flight_id bigint not null references flights(id),
    seat_number varchar(10) not null,
    status varchar(30) not null,
    constraint uk_booking_flight_seat unique (flight_id, seat_number)
);
