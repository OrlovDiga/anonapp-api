CREATE TABLE user_entity (
    id bigint PRIMARY KEY,
    created timestamp without time zone,
    login character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    status character varying(255),
    token character varying(255),
    updated timestamp without time zone,
    username character varying(255) NOT NULL
);