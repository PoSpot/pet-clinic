create table owner (id bigint not null auto_increment, first_name varchar(255), last_name varchar(255), address varchar(255), city varchar(255), telephone varchar(255), primary key (id)) engine=InnoDB;
create table pet (id bigint not null auto_increment, birth_date date, name varchar(255), owner_id bigint, type_id bigint, primary key (id)) engine=InnoDB;
create table pet_type (id bigint not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB;
create table speciality (id bigint not null auto_increment, description varchar(255), primary key (id)) engine=InnoDB;
create table vet (id bigint not null auto_increment, first_name varchar(255), last_name varchar(255), primary key (id)) engine=InnoDB;
create table vet_specialities (vet_id bigint not null, speciality_id bigint not null, primary key (vet_id, speciality_id)) engine=InnoDB;
create table visit (id bigint not null auto_increment, date date, description varchar(255), pet_id bigint, primary key (id)) engine=InnoDB;
alter table pet add constraint FK7qfti9yba86tgfe9oobeqxfxg foreign key (owner_id) references owner (id);
alter table pet add constraint FKrem5eg7ygof60lwmbo8gif3qg foreign key (type_id) references pet_type (id);
alter table vet_specialities add constraint FKos9a3pvvr9tmwg7102f2egk2 foreign key (speciality_id) references speciality (id);
alter table vet_specialities add constraint FKi3dhocjo7mhx8vsdamkccei46 foreign key (vet_id) references vet (id);
alter table visit add constraint FKpr2103nfb1ueid78p80lvv1ed foreign key (pet_id) references pet (id);
