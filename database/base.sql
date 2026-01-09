-- \i /home/antonio/ITU/S5/md-baovola/aerienne/database/base.sql
\c postgres;
drop database if exists aerienne;

create database aerienne;
\c aerienne;

CREATE TABLE aeroport(
   Id_aeroport SERIAL,
   code_IATA VARCHAR(50) ,
   PRIMARY KEY(Id_aeroport)
);

CREATE TABLE avion(
   Id_avion SERIAL,
   capacité NUMERIC(15,2)  ,
   modele VARCHAR(50) ,
   PRIMARY KEY(Id_avion)
);

CREATE TABLE modele_vol(
   Id_modele_vol SERIAL,
   numero_vol VARCHAR(50) ,
   Id_aeroport INTEGER NOT NULL,
   Id_aeroport_1 INTEGER NOT NULL,
   PRIMARY KEY(Id_modele_vol),
   FOREIGN KEY(Id_aeroport) REFERENCES aeroport(Id_aeroport),
   FOREIGN KEY(Id_aeroport_1) REFERENCES aeroport(Id_aeroport)
);

CREATE TABLE vol(
   Id_vol SERIAL,
   date_heure_depart TIMESTAMP,
   date_heure_arrive TIMESTAMP,
   Id_modele_vol INTEGER NOT NULL,
   PRIMARY KEY(Id_vol),
   FOREIGN KEY(Id_modele_vol) REFERENCES modele_vol(Id_modele_vol)
);

CREATE TABLE vol_avion(
   Id_avion INTEGER,
   Id_vol INTEGER,
   date_heure_depart TIMESTAMP,
   date_heure_arrive TIMESTAMP,
   PRIMARY KEY(Id_avion, Id_vol),
   FOREIGN KEY(Id_avion) REFERENCES avion(Id_avion),
   FOREIGN KEY(Id_vol) REFERENCES vol(Id_vol)
);