-- \i /home/antonio/ITU/S5/md-baovola/aerienne-itambarana/database/base.sql
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
   capacite NUMERIC(15,2),
   modele VARCHAR(50) ,
   PRIMARY KEY(Id_avion)
);

CREATE TABLE trajet(
   Id_trajet SERIAL,
   Id_aeroport_depart INTEGER NOT NULL,
   Id_aeroport_arrive INTEGER NOT NULL,
   PRIMARY KEY(Id_trajet),
   FOREIGN KEY(Id_aeroport_depart) REFERENCES aeroport(Id_aeroport),
   FOREIGN KEY(Id_aeroport_arrive) REFERENCES aeroport(Id_aeroport)
);

CREATE TABLE vol(
   Id_vol SERIAL,
   Id_avion INTEGER NOT NULL,
   date_heure_depart TIMESTAMP,
   date_heure_arrive TIMESTAMP,
   Id_trajet INTEGER NOT NULL,
   PRIMARY KEY(Id_vol),
   FOREIGN KEY (Id_avion) REFERENCES avion(Id_avion),
   FOREIGN KEY(Id_trajet) REFERENCES trajet(Id_trajet)
);
CREATE TABLE classe_place(
   Id_classe_place SERIAL,
   libelle VARCHAR(50),
   PRIMARY KEY(Id_classe_place)
);

CREATE TABLE categorie_client(
   Id_categorie_client SERIAL,
   libelle VARCHAR(50),
   PRIMARY KEY(Id_categorie_client)
);

CREATE TABLE achat_places(
   Id_achat_places SERIAL,
   Id_classe_place INTEGER,
   Id_categorie_client INTEGER,
   nom_client VARCHAR(50) ,
   nombre_place VARCHAR(50) ,
   Id_vol INTEGER NOT NULL,
   PRIMARY KEY(Id_achat_places),
   FOREIGN KEY(Id_vol) REFERENCES vol(Id_vol),
   FOREIGN KEY(Id_classe_place) REFERENCES classe_place(Id_classe_place),
   FOREIGN KEY(Id_categorie_client) REFERENCES categorie_client(Id_categorie_client)
);


CREATE TABLE prix_vol(
   Id_prix_vol SERIAL,
   Id_vol INTEGER NOT NULL,
   Id_classe_place INTEGER NOT NULL,
   prix NUMERIC(15,2) DEFAULT 0,
   nbr_places INTEGER DEFAULT 0,
   PRIMARY KEY(Id_prix_vol),
   FOREIGN KEY(Id_vol) REFERENCES vol(Id_vol),
   FOREIGN KEY(Id_classe_place) REFERENCES classe_place(Id_classe_place)
);

CREATE TABLE prix_par_categorie(
   Id_prix_categorie SERIAL,
   Id_vol INTEGER NOT NULL,
   Id_categorie_personne INTEGER NOT NULL,
   Id_classe_place INTEGER NOT NULL,
   prix NUMERIC(15,2) DEFAULT NULL,
   pourcentage NUMERIC(15,2) DEFAULT 0, -- Pourcentage de prix par rapport au prix_vol
   PRIMARY KEY(Id_prix_categorie),
   FOREIGN KEY(Id_vol) REFERENCES vol(Id_vol),
   FOREIGN KEY(Id_categorie_personne) REFERENCES categorie_client(Id_categorie_client),
   FOREIGN KEY(Id_classe_place) REFERENCES classe_place(Id_classe_place)
);

CREATE TABLE cout_diffusion (
   Id_cout_diffusion SERIAL,
   cout_unitaire NUMERIC(15,2),
   PRIMARY KEY(Id_cout_diffusion)
);
CREATE TABLE societe (
   Id_societe SERIAL,
   nom VARCHAR(100),
   PRIMARY KEY(Id_societe)
);
CREATE TABLE diffusion_publicitaire (
   Id_diffusion_publicitaire SERIAL,
   Id_societe INTEGER NOT NULL,
   Id_vol INTEGER NOT NULL,
   mois int,
   annee int,
   nombre_diffusion INTEGER,
   PRIMARY KEY(Id_diffusion_publicitaire),
   FOREIGN KEY(Id_societe) REFERENCES societe(Id_societe),
   FOREIGN KEY(Id_vol) REFERENCES vol(Id_vol)
);

CREATE TABLE paiement_pub (
   Id_paiement_pub SERIAL,
   date_paiement TIMESTAMP,
   montant NUMERIC(15,2),
   Id_societe INTEGER NOT NULL,
   PRIMARY KEY(Id_paiement_pub),
   FOREIGN KEY(Id_societe) REFERENCES societe(Id_societe)
);