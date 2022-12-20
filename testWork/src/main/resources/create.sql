create table meta_inf_product (id bigint not null, is_discount boolean not null, primary key (id))
create table product (id bigint not null, amount bigint, local_date date, name varchar(255), price float(53) not null,discount_id bigint not null, primary key (id))
