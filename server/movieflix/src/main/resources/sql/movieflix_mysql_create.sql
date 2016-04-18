
CREATE TABLE `movie` (
	`id` bigint AUTO_INCREMENT,
	`title` TEXT NOT NULL,
	`year` numeric(4) NOT NULL,
	`rated` TEXT(10),
	`released` DATE NOT NULL,
	`runminutes` numeric(10) NOT NULL,
	`plot` TEXT NOT NULL,
	`language` TEXT NOT NULL,
	`country` TEXT NOT NULL,
	`awardsid` bigint NOT NULL,
	`poster_url` TEXT NOT NULL,
	`typeid` bigint NOT NULL,
	`imdbid` bigint NOT NULL,
	PRIMARY KEY (`id`)
);



CREATE TABLE `genre` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(30) NOT NULL UNIQUE,
	PRIMARY KEY (`id`,`name`)
);

CREATE TABLE `cast` (
	`id` bigint NOT NULL,
	`name` VARCHAR(30) NOT NULL UNIQUE,
	PRIMARY KEY (`id`,`name`)
);

CREATE TABLE `type` (
	`id` bigint NOT NULL,
	`name` VARCHAR(30) NOT NULL UNIQUE,
	PRIMARY KEY (`id`,`name`)
);

CREATE TABLE `casttable` (
	`id` bigint NOT NULL,
	`movie_id` bigint NOT NULL,
	PRIMARY KEY (`id`,`movie_id`)
);

CREATE TABLE `genretable` (
	`id` bigint NOT NULL,
	`movie_id` bigint NOT NULL,
	PRIMARY KEY (`id`,`movie_id`)
);

CREATE TABLE `user` (
	`id` bigint AUTO_INCREMENT,
	`firstname` TEXT NOT NULL,
	`lastname` TEXT NOT NULL,
	`email` VARCHAR(200) NOT NULL UNIQUE,
	`password` TEXT NOT NULL,
	`country` TEXT NOT NULL,
	`role_id` bigint NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `awards` (
	`id` bigint AUTO_INCREMENT,
	`wins` bigint NOT NULL,
	`oscars` bigint NOT NULL,
	`nominations` bigint NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `ratings` (
	`ratings` bigint NOT NULL,
	`movie_id` bigint NOT NULL,
	`user_id` bigint NOT NULL,
	PRIMARY KEY (`movie_id`,`user_id`)
);

CREATE TABLE `comments` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`user_id` bigint NOT NULL ,
	`comment` TEXT NOT NULL,
	`movie_id` bigint NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `actors` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(30) NOT NULL UNIQUE,
	PRIMARY KEY (`id`,`name`)
);

CREATE TABLE `actorstable` (
	`actor_id` bigint NOT NULL,
	`movie_id` bigint NOT NULL,
	PRIMARY KEY (`actor_id`,`movie_id`)
);

CREATE TABLE `imdb` (
	`id` bigint  AUTO_INCREMENT,
	`votes` numeric NOT NULL ,
	`imdbid` VARCHAR(50) NOT NULL UNIQUE,
	`metascore` numeric NOT NULL ,
	`movie_id` bigint NOT NULL ,
	PRIMARY KEY (`id`)
);

CREATE TABLE `role` (
	`id` bigint  AUTO_INCREMENT,
	`name` VARCHAR(50) NOT NULL UNIQUE  ,
	PRIMARY KEY (`id`,`name`)
);

ALTER TABLE `movie` ADD CONSTRAINT `movie_fk0` FOREIGN KEY (`awardsid`) REFERENCES `awards`(`id`);

ALTER TABLE `movie` ADD CONSTRAINT `movie_fk1` FOREIGN KEY (`typeid`) REFERENCES `type`(`id`);

ALTER TABLE `movie` ADD CONSTRAINT `movie_fk2` FOREIGN KEY (`imdbid`) REFERENCES `imdb`(`id`);

ALTER TABLE `casttable` ADD CONSTRAINT `casttable_fk0` FOREIGN KEY (`id`) REFERENCES `cast`(`id`);

ALTER TABLE `casttable` ADD CONSTRAINT `casttable_fk1` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`);

ALTER TABLE `genretable` ADD CONSTRAINT `genretable_fk0` FOREIGN KEY (`id`) REFERENCES `genre`(`id`);

ALTER TABLE `genretable` ADD CONSTRAINT `genretable_fk1` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`);

ALTER TABLE `user` ADD CONSTRAINT `user_fk0` FOREIGN KEY (`role_id`) REFERENCES `role`(`id`);

ALTER TABLE `ratings` ADD CONSTRAINT `ratings_fk0` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`);

ALTER TABLE `ratings` ADD CONSTRAINT `ratings_fk1` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);

ALTER TABLE `comments` ADD CONSTRAINT `comments_fk0` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);

ALTER TABLE `comments` ADD CONSTRAINT `comments_fk1` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`);

ALTER TABLE `actorstable` ADD CONSTRAINT `actorstable_fk0` FOREIGN KEY (`actor_id`) REFERENCES `actors`(`id`);

ALTER TABLE `actorstable` ADD CONSTRAINT `actorstable_fk1` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`);

ALTER TABLE `imdb` ADD CONSTRAINT `imdb_fk0` FOREIGN KEY (`movie_id`) REFERENCES `movie`(`id`);

