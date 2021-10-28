create table products
(
    id                  serial primary key,
    product_name        varchar(100),
    product_price       varchar(100),
    product_description varchar(1000),
    product_category    varchar(100)
);