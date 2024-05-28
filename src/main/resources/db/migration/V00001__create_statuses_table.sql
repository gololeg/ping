
CREATE TABLE ping.statuses
(
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    CONSTRAINT statuses_pkey PRIMARY KEY (id)
);
INSERT INTO ping.statuses(
	id, name)
	VALUES (1, 'Запланирован');
	INSERT INTO ping.statuses(
  	id, name)
  	VALUES (2, 'Выполняется');
  	INSERT INTO ping.statuses(
    	id, name)
    	VALUES (3, 'Выполнен');