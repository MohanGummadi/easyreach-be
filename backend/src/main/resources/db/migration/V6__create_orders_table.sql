create table if not exists orders (
    id serial primary key,
    order_id varchar(255) not null unique,
    address varchar(255),
    full_address text,
    customer_name varchar(255),
    customer_mobile varchar(255),
    sand_quantity varchar(50),
    supply_point varchar(255),
    trip_no integer default 0,
    created_at timestamptz not null,
    updated_at timestamptz not null
);
