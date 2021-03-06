create table product (
  id numeric generated by default as identity (start with 1, increment by 1),
  name varchar(200),
  description varchar(2000),
  price numeric(10,2),
  file_content varchar(500),
  file_size numeric,
  primary key (id)
);

create table purchase (
  id numeric generated by default as identity (start with 1, increment by 1),
  customer_name varchar(120),
  customer_email varchar(150),
  primary key(id)
);

create table purchase_product (
  product_id numeric not null,
  purchase_id numeric not null,
  primary key(product_id, purchase_id)
);
