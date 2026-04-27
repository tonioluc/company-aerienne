-- effacer tous les donnees existantes
-- 1. BASE
DELETE FROM paiement_facture;
DELETE FROM facture_diffusion;
DELETE FROM facture_mere;
DELETE FROM diffusion_publicitaire;

DELETE FROM vente_produit_extra;
DELETE FROM achat_places;
DELETE FROM prix_par_categorie;
DELETE FROM prix_vol;
DELETE FROM cout_diffusion;

DELETE FROM vol;
DELETE FROM trajet;
DELETE FROM avion;
DELETE FROM aeroport;

DELETE FROM produit_extra;
DELETE FROM classe_place;
DELETE FROM categorie_client;
DELETE FROM societe;
INSERT INTO aeroport (code_IATA, ville) VALUES
('TNR', 'Antananarivo'),
('NOS', 'Nosy Be');

INSERT INTO avion (capacite, modele) VALUES
(200, 'ATR - 045');

-- Trajets (sans numero_vol selon base.sql)
INSERT INTO trajet (Id_aeroport_depart, Id_aeroport_arrive) VALUES
(1, 2); -- TNR -> NOS

-- Vols (sans prix_place, selon base.sql)
INSERT INTO vol (date_heure_depart, date_heure_arrive, Id_avion, Id_trajet) VALUES 
('2026-01-20 10:00:00', '2026-01-20 12:00:00', 1, 1),
('2026-01-21 10:00:00', '2026-01-21 12:00:00', 1, 1),
('2026-01-21 15:00:00', '2026-01-21 17:00:00', 1, 1);

-- Classes de place
INSERT INTO classe_place (libelle) VALUES
('Economique'),
('Premium'),
('Premiere');
-- =====================================================
-- Vol Antananarivo (TNR) -> Nosy Be (NOS)
-- Avion de 120 places avec 3 classes
-- =====================================================

-- Prix par classe pour le vol TNR -> NOS (Id_vol = 1)
-- Première (id 3): 30 places à 1 200 000 Ar
-- Premium (id 2): 40 places à 1 000 000 Ar  
-- Économique (id 1): 50 places à 700 000 Ar
INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places) VALUES
(1, 3, 2000000, 30),  -- Première
(1, 2, 1000000, 40),  -- Premium
(1, 1, 800000, 50),   -- Économique

(2, 3, 2000000, 30),  -- Première
(2, 2, 1000000, 40),  -- Premium
(2, 1, 800000, 50),   -- Économique

(3, 3, 2000000, 30),  -- Première
(3, 2, 1000000, 40),  -- Premium
(3, 1, 800000, 50);   -- Économique

INSERT INTO categorie_client (libelle) VALUES
('Adulte'),
('Enfant'),
('Bébé');

-- Enfant (id=2): prix fixe (le champ prix est utilisé en priorité)
INSERT INTO prix_par_categorie (Id_categorie_personne,id_vol, Id_classe_place, prix, pourcentage) VALUES
(2,1, 1, 600000, NULL),  -- Enfant: Économique à 600 000 Ar (prix fixe)
(2,1, 2, 700000, NULL),  -- Enfant: Premium à 700 000 Ar (prix fixe)
(2,1, 3, 800000, NULL); -- Enfant: Première à 800 000 Ar (prix fixe)

-- Bébé (id=3): pourcentage du prix de base (prix = NULL donc on utilise pourcentage)
INSERT INTO prix_par_categorie (Id_categorie_personne,id_vol, Id_classe_place, prix, pourcentage) VALUES
(3,1, 1, NULL, 10),  -- Bébé: Économique à 10% de 800 000 = 80 000 Ar
(3,1, 2, NULL, 10),  -- Bébé: Premium à 10% de 700 000 = 70 000 Ar
(3,1, 3, NULL, 10);  -- Bébé: Première à 10% de 2000000 = 200000 Ar

-- Les identifiants de catégorie utilisés ici : (1 = Adulte, 2 = Enfant, 3 = Bébé)
-- INSERT INTO achat_places (Id_classe_place, Id_categorie_client, nom_client, nombre_place, Id_vol) VALUES
-- 	-- Première
-- 	(3, 3, 'Jean', '2', 1),   -- Bébé (3), Premiere, 2 places (prix = 10% du prix de base)
-- 	(3, 2, 'Jean', '4', 1),   -- Enfant (2), Premiere, 4 places (prix fixe enfant)
-- 	(3, 1, 'Jean', '10', 1), -- Adulte (1), Premiere, 10 places (prix de base)
-- 	-- Premium
-- 	(2, 3, 'Jean', '4', 1),   -- Bébé (3), Premium, 4 places (10% du prix)
-- 	(2, 2, 'Jean', '5', 1),   -- Enfant (2), Premium, 5 places (prix fixe enfant)
-- 	(2, 1, 'Jean', '20', 1), -- Adulte (1), Premium, 20 places (prix de base)
-- 	-- Economique
-- 	(1, 3, 'Jean', '4', 1),   -- Bébé (3), Economique, 4 places (10% du prix)
-- 	(1, 2, 'Jean', '10', 1),  -- Enfant (2), Economique, 10 places (prix fixe enfant)
-- 	(1, 1, 'Jean', '30', 1); -- Adulte (1), Economique, 30 places (prix de base)

	-- Test data pour societe et cout_diffusion
	INSERT INTO societe (nom) VALUES
	('Vaniala'),
	('Lewis'),
	('Socobis'),
	('Jejoo');

	INSERT INTO cout_diffusion (cout_unitaire) VALUES
	(400000);

	-- Achats correspondant à l'image : billets adulte, classe Economique (Id_classe_place = 1, Id_categorie_client = 1)
	INSERT INTO achat_places (Id_classe_place, Id_categorie_client, nom_client, nombre_place, Id_vol) VALUES
	(1, 1, 'Jean', 40, 1), -- 20 janvier 2026 - 10h : 40 Adulte Économique (vol 1)
	(1, 1, 'Jean', 30, 2), -- 21 janvier 2026 - 10h : 30 Adulte Économique (vol 2)
	(1, 1, 'Jean', 50, 3); -- 21 janvier 2026 - 15h : 50 Adulte Économique (vol 3)

	INSERT INTO produit_extra (nom, prix_unitaire) VALUES
	('Chocolat', 5000);
	