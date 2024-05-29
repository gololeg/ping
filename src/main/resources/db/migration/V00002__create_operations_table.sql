CREATE SEQUENCE IF NOT EXISTS ping.operations_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
CREATE TABLE ping.operations
(
    id integer NOT NULL DEFAULT nextval('ping.operations_id_seq'::regclass),
    domain character varying(100) NOT NULL,
    result character varying(1000),
    create_date timestamp with time zone NOT NULL,
    status_id integer NOT NULL,
    CONSTRAINT operations_pkey PRIMARY KEY (id),
    CONSTRAINT fk_operations_status FOREIGN KEY (status_id)
     REFERENCES ping.statuses (id)
)