CREATE TABLE IF NOT EXISTS products(
    product_id SERIAL primary key,
    product_barcode UUID not null,
    batch_id integer not null,
    product_name varchar(100) not null,
    product_desc varchar(200),
    price numeric(16, 4) not null,
    discount numeric(16, 4),
    discounted_price numeric(16, 4)
);