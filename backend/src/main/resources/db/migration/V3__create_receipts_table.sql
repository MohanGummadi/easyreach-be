CREATE TABLE public.receipts (
    id BIGSERIAL PRIMARY KEY,
    order_id VARCHAR(50),
    trip_no VARCHAR(50),
    customer_name VARCHAR(255),
    customer_mobile VARCHAR(50),
    sand_quantity VARCHAR(50),
    supply_point VARCHAR(255),
    dispatch_date_time TIMESTAMP,
    driver_name VARCHAR(255),
    driver_mobile VARCHAR(50),
    vehicle_no VARCHAR(50),
    address TEXT,
    footer_line VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

