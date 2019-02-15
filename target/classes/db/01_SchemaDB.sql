CREATE TABLE `User` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`firstname` varchar(16) NOT NULL,
	`lastname` varchar(16) NOT NULL,
	`adress_id` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Adress` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`country_id` INT NOT NULL,
	`city_id` INT NOT NULL,
	`street_id` INT NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Country` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(64) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `City` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(64) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Street` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(64) NOT NULL,
	PRIMARY KEY (`id`)
);