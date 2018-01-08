create table student
(
   id integer not null,
   name varchar(255),
   passport_number varchar(255),
   primary key(id)
);

create table student_dense
(
   id integer not null,
   name varchar(255),
   passport_number varchar(255),
   primary key(id)
);

create table student_missing
(
   id integer not null,
   missing_columns varchar(255),
   primary key(id)
);