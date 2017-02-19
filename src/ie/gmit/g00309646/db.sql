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
	totalPrice DOUBLE NOT NULL,
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

INSERT INTO bus_table (bus_number, depart_from, going_to, bus_time) VALUES 
("X1", "Galway", "Dublin", "9.45AM"),
("X2", "Galway", "Cork", "9.45AM"),
("X3", "Galway", "Limerick", "9.45AM"),
("X1", "Dublin", "Galway", "9.45AM"),
("X4", "Dublin", "Cork", "9.45AM"),
("X5", "Dublin", "Limerick", "9.45AM"),
("X2", "Cork", "Galway", "9.45AM"),
("X4", "Cork", "Dublin", "9.45AM"),
("X6", "Cork", "Limerick", "9.45AM"),
("X3", "Limerick", "Galway", "9.45AM"),
("X5", "Limerick", "Dublin", "9.45AM"),
("X6", "Limerick", "Cork", "9.45AM"),

("X1", "Galway", "Dublin", "11.40AM"),
("X2", "Galway", "Cork", "11.40AM"),
("X3", "Galway", "Limerick", "11.40AM"),
("X1", "Dublin", "Galway", "11.40AM"),
("X4", "Dublin", "Cork", "11.40AM"),
("X5", "Dublin", "Limerick", "11.40AM"),
("X2", "Cork", "Galway", "11.40AM"),
("X4", "Cork", "Dublin", "11.40AM"),
("X6", "Cork", "Limerick", "11.40AM"),
("X3", "Limerick", "Galway", "11.40AM"),
("X5", "Limerick", "Dublin", "11.40AM"),
("X6", "Limerick", "Cork", "11.40AM"),

("X1", "Galway", "Dublin", "1.05PM"),
("X2", "Galway", "Cork", "1.05PM"),
("X3", "Galway", "Limerick", "1.05PM"),
("X1", "Dublin", "Galway", "1.05PM"),
("X4", "Dublin", "Cork", "1.05PM"),
("X5", "Dublin", "Limerick", "1.05PM"),
("X2", "Cork", "Galway", "1.05PM"),
("X4", "Cork", "Dublin", "1.05PM"),
("X6", "Cork", "Limerick", "1.05PM"),
("X3", "Limerick", "Galway", "1.05PM"),
("X5", "Limerick", "Dublin", "1.05PM"),
("X6", "Limerick", "Cork", "1.05PM"),

("X1", "Galway", "Dublin", "3.15PM"),
("X2", "Galway", "Cork", "3.15PM"),
("X3", "Galway", "Limerick", "3.15PM"),
("X1", "Dublin", "Galway", "3.15PM"),
("X4", "Dublin", "Cork", "3.15PM"),
("X5", "Dublin", "Limerick", "3.15PM"),
("X2", "Cork", "Galway", "3.15PM"),
("X4", "Cork", "Dublin", "3.15PM"),
("X6", "Cork", "Limerick", "3.15PM"),
("X3", "Limerick", "Galway", "3.15PM"),
("X5", "Limerick", "Dublin", "3.15PM"),
("X6", "Limerick", "Cork", "3.15PM"),

("X1", "Galway", "Dublin", "5.25PM"),
("X2", "Galway", "Cork", "5.25PM"),
("X3", "Galway", "Limerick", "5.25PM"),
("X1", "Dublin", "Galway", "5.25PM"),
("X4", "Dublin", "Cork", "5.25PM"),
("X5", "Dublin", "Limerick", "5.25PM"),
("X2", "Cork", "Galway", "5.25PM"),
("X4", "Cork", "Dublin", "5.25PM"),
("X6", "Cork", "Limerick", "5.25PM"),
("X3", "Limerick", "Galway", "5.25PM"),
("X5", "Limerick", "Dublin", "5.25PM"),
("X6", "Limerick", "Cork", "5.25PM"),

("X1", "Galway", "Dublin", "7.45PM"),
("X2", "Galway", "Cork", "7.45PM"),
("X3", "Galway", "Limerick", "7.45PM"),
("X1", "Dublin", "Galway", "7.45PM"),
("X4", "Dublin", "Cork", "7.45PM"),
("X5", "Dublin", "Limerick", "7.45PM"),
("X2", "Cork", "Galway", "7.45PM"),
("X4", "Cork", "Dublin", "7.45PM"),
("X6", "Cork", "Limerick", "7.45PM"),
("X3", "Limerick", "Galway", "7.45PM"),
("X5", "Limerick", "Dublin", "7.45PM"),
("X6", "Limerick", "Cork", "7.45PM"),

("X1", "Galway", "Dublin", "9.55PM"),
("X2", "Galway", "Cork", "9.55PM"),
("X3", "Galway", "Limerick", "9.55PM"),
("X1", "Dublin", "Galway", "9.55PM"),
("X4", "Dublin", "Cork", "9.55PM"),
("X5", "Dublin", "Limerick", "9.55PM"),
("X2", "Cork", "Galway", "9.55PM"),
("X4", "Cork", "Dublin", "9.55PM"),
("X6", "Cork", "Limerick", "9.55PM"),
("X3", "Limerick", "Galway", "9.55PM"),
("X5", "Limerick", "Dublin", "9.55PM"),
("X6", "Limerick", "Cork", "9.55PM"),

("X1", "Galway", "Dublin", "11.25PM"),
("X2", "Galway", "Cork", "11.25PM"),
("X3", "Galway", "Limerick", "11.25PM"),
("X1", "Dublin", "Galway", "11.25PM"),
("X4", "Dublin", "Cork", "11.25PM"),
("X5", "Dublin", "Limerick", "11.25PM"),
("X2", "Cork", "Galway", "11.25PM"),
("X4", "Cork", "Dublin", "11.25PM"),
("X6", "Cork", "Limerick", "11.25PM"),
("X3", "Limerick", "Galway", "11.25PM"),
("X5", "Limerick", "Dublin", "11.25PM"),
("X6", "Limerick", "Cork", "11.25PM");
