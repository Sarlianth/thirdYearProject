Drop database if exists project;
create database project CHARACTER SET utf8 COLLATE UTF8_GENERAL_CI;
Use project;

Show tables;
Drop table if exists bus_table;

create table bus_table (
	bus_id INT NOT NULL AUTO_INCREMENT,
	bus_number varchar(30) NOT NULL,
	depart_from varchar(60) NOT NULL,
	going_to varchar(60) NOT NULL,
	bus_time varchar(10) NOT NULL,
	PRIMARY KEY (bus_id)
);

Drop table if exists ticket;

create table ticket (
	bus_id INT NOT NULL,
	bus_number varchar(30) NOT NULL,
	journey_date varchar(60) NOT NULL,
	adult_quantity INT NOT NULL,
	student_quantity INT NOT NULL,
	user_id INT NOT NULL,
	ticket_id INT NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (ticket_id)
);

Drop table if exists users;

create table users (
	id INT NOT NULL,
	name varchar(60) NOT NULL,
	surname varchar(60) NOT NULL,
	username varchar(60) NOT NULL,
	password varchar(60) NOT NULL,
	is_admin boolean DEFAULT false,
	PRIMARY KEY (id)
);

ALTER TABLE ticket ADD CONSTRAINT ticket_fk0 FOREIGN KEY (bus_id) REFERENCES bus_table(bus_id);

ALTER TABLE ticket ADD CONSTRAINT ticket_fk1 FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ticket AUTO_INCREMENT=543200;

INSERT INTO users VALUES 
(1, "Adrian", "Sypos", "admin", "admin", true), 
(2, "John", "Murphy", "user", "pass", false);

INSERT INTO bus_table VALUES 
(1, "X1", "Galway", "Dublin", "12.00am"),
(2, "X2", "Galway", "Cork", "12.00am"),
(3, "X3", "Galway", "Limerick", "12.00am"),
(4, "X1", "Dublin", "Galway", "12.00am"),
(5, "X4", "Dublin", "Cork", "12.00am"),
(6, "X5", "Dublin", "Limerick", "12.00am"),
(7, "X2", "Cork", "Galway", "12.00am"),
(8, "X4", "Cork", "Dublin", "12.00am"),
(9, "X6", "Cork", "Limerick", "12.00am"),
(10, "X3", "Limerick", "Galway", "12.00am"),
(11, "X5", "Limerick", "Dublin", "12.00am"),
(12, "X6", "Limerick", "Cork", "12.00am");
