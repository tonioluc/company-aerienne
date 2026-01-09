-- \i /home/antonio/ITU/S5/md-baovola/aerienne/database/base.sql
\c postgres;
drop database if exists aerienne;

create database aerienne;
\c aerienne;

CREATE TABLE aeroport(
   Id_aeroport SERIAL,
   code_IATA VARCHAR(50),
   ville VARCHAR(100),
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
   Id_aeroport_depart INTEGER NOT NULL,
   Id_aeroport_arrive INTEGER NOT NULL,
   PRIMARY KEY(Id_modele_vol),
   FOREIGN KEY(Id_aeroport_depart) REFERENCES aeroport(Id_aeroport),
   FOREIGN KEY(Id_aeroport_arrive) REFERENCES aeroport(Id_aeroport)
);

CREATE TABLE vol(
   Id_vol SERIAL,
   date_heure_depart TIMESTAMP,
   date_heure_arrive TIMESTAMP,
   prix_place NUMERIC(15,2) DEFAULT 0,
   Id_modele_vol INTEGER NOT NULL,
   PRIMARY KEY(Id_vol),
   FOREIGN KEY(Id_modele_vol) REFERENCES modele_vol(Id_modele_vol)
);

CREATE TABLE achat_places(
   Id_achat_places SERIAL,
   nom_client VARCHAR(50) ,
   nombre_place VARCHAR(50) ,
   Id_vol INTEGER NOT NULL,
   PRIMARY KEY(Id_achat_places),
   FOREIGN KEY(Id_vol) REFERENCES vol(Id_vol)
);


CREATE TABLE vol_avion(
   Id_avion INTEGER,
   Id_vol INTEGER,
   numero INTEGER,
   PRIMARY KEY(Id_avion, Id_vol),
   FOREIGN KEY(Id_avion) REFERENCES avion(Id_avion),
   FOREIGN KEY(Id_vol) REFERENCES vol(Id_vol)
);

-- Vue pour calculer le chiffre d'affaires par vol
CREATE VIEW v_chiffre_affaires_vol AS
SELECT 
   v.Id_vol,
   v.date_heure_depart,
   v.date_heure_arrive,
   v.prix_place,
   mv.numero_vol,
   COALESCE(SUM(CAST(ap.nombre_place AS INTEGER)), 0) AS total_places_vendues,
   COALESCE(SUM(CAST(ap.nombre_place AS INTEGER) * v.prix_place), 0) AS chiffre_affaires
FROM vol v
JOIN modele_vol mv ON v.Id_modele_vol = mv.Id_modele_vol
LEFT JOIN achat_places ap ON v.Id_vol = ap.Id_vol
GROUP BY v.Id_vol, v.date_heure_depart, v.date_heure_arrive, v.prix_place, mv.numero_vol;

-- Vue pour le chiffre d'affaires global
CREATE VIEW v_chiffre_affaires_total AS
SELECT 
   SUM(chiffre_affaires) AS chiffre_affaires_total,
   SUM(total_places_vendues) AS total_places_vendues
FROM v_chiffre_affaires_vol;
