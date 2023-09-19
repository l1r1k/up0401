create database lab3;

CREATE TABLE IF NOT EXISTS public.marks
(
    id_mark bigint NOT NULL,
    name_mark character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT marks_pkey PRIMARY KEY (id_mark)
)
CREATE TABLE IF NOT EXISTS public.models
(
    id_model bigint NOT NULL,
    name_model character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT models_pkey PRIMARY KEY (id_model)
)
CREATE TABLE IF NOT EXISTS public.shops
(
    id_shop bigint NOT NULL,
    address_shop character varying(255) COLLATE pg_catalog."default",
    name_shop character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT shops_pkey PRIMARY KEY (id_shop)
)
CREATE TABLE IF NOT EXISTS public.users
(
    id_user bigint NOT NULL,
    first_name_user character varying(32) COLLATE pg_catalog."default",
    last_name_user character varying(32) COLLATE pg_catalog."default",
    login_user character varying(32) COLLATE pg_catalog."default",
    password_user character varying(255) COLLATE pg_catalog."default",
    second_name_user character varying(32) COLLATE pg_catalog."default",
    active boolean NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id_user)
)
CREATE TABLE IF NOT EXISTS public.user_role
(
    user_id bigint NOT NULL,
    roles character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT fkj345gk1bovqvfame88rcx7yyx FOREIGN KEY (user_id)
        REFERENCES public.users (id_user) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
CREATE TABLE IF NOT EXISTS public.orders
(
    id_order bigint NOT NULL,
    date_order date,
    number_order character varying(255) COLLATE pg_catalog."default",
    status_order boolean NOT NULL,
    product character varying(255) COLLATE pg_catalog."default",
    user_id bigint,
    product_id bigint,
    CONSTRAINT orders_pkey PRIMARY KEY (id_order),
    CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id)
        REFERENCES public.users (id_user) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkkp5k52qtiygd8jkag4hayd0qg FOREIGN KEY (product_id)
        REFERENCES public.products (id_product) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
CREATE TABLE IF NOT EXISTS public.products
(
    id_product bigint NOT NULL,
    article_product character varying(8) COLLATE pg_catalog."default",
    cost_product double precision NOT NULL,
    count_product integer NOT NULL,
    mark_id bigint,
    model_id bigint,
    shop_id bigint,
    CONSTRAINT products_pkey PRIMARY KEY (id_product),
    CONSTRAINT fk29c4nbv58vgu9wg14fd8ac4xy FOREIGN KEY (model_id)
        REFERENCES public.models (id_model) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk7kp8sbhxboponhx3lxqtmkcoj FOREIGN KEY (shop_id)
        REFERENCES public.shops (id_shop) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkm2glqg5xaucu0m4hymqe82nuo FOREIGN KEY (mark_id)
        REFERENCES public.marks (id_mark) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)