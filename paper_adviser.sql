create table users(
	id_user serial NOT NULL,
	first_name varchar(20) NOT NULL,
	last_name varchar(30) NOT NULL,
	senha varchar(50) NOT NULL,
	primary key (id_user));

create table papers(
	id_paper serial NOT NULL,
	name_paper varchar(30) NOT NULL,
	info text,
	primary key (id_paper));

create table rate(
	id_rate serial NOT NULL,
	id_user serial NOT NULL,
	id_paper serial NOT NULL,
	rating numeric,
	primary key (id_rate),
	foreign key (id_user) REFERENCES users,
	foreign key (id_paper) REFERENCES papers);

create table paper_comment(
	id_comment serial NOT NULL,
	id_paper serial NOT NULL,
	id_user serial NOT NULL,
	info text,
	date_comment date NOT NULL,
	primary key (id_comment),
	foreign key (id_paper) REFERENCES papers,
	foreign key (id_user) REFERENCES users);
	
	
	