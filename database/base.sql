-- \i /home/antonio/ITU/S5/md-baovola/aerienne/database/base.sql
\c postgres;
drop database if exists aerienne;

create database aerienne;
\c aerienne;

create table avions (
    id serial primary key,
    modele varchar(50) not null,
    capacite int not null
);
