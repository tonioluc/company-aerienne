INSERT INTO aeroport (code_IATA, ville) VALUES
('TNR', 'Antananarivo'),
('DZA', 'Dzaoudzi'),
('RUN', 'Saint-Denis'),
('CDG', 'Paris'),
('JNB', 'Johannesburg'),
('NOS', 'Nosy Be');

INSERT INTO avion (capacite, modele) VALUES
(180, 'Boeing 737'),
(220, 'Airbus A320'),
(300, 'Boeing 787'),
(150, 'ATR 72'),
(120, 'Embraer E175');

-- Trajets (sans numero_vol selon base.sql)
INSERT INTO trajet (Id_aeroport_depart, Id_aeroport_arrive) VALUES
(1, 2), -- TNR -> DZA
(1, 3), -- TNR -> RUN
(4, 1), -- CDG -> TNR
(1, 5), -- TNR -> JNB
(1, 6); -- TNR -> NOS

-- Vols (sans prix_place, selon base.sql)
INSERT INTO vol (date_heure_depart, date_heure_arrive, Id_avion, Id_trajet) VALUES
('2026-02-01 08:00:00', '2026-02-01 10:30:00', 1, 1), -- Vol 1: Boeing 737 sur trajet 1
('2026-02-02 14:00:00', '2026-02-02 16:45:00', 2, 2), -- Vol 2: Airbus A320 sur trajet 2
('2026-02-03 21:00:00', '2026-02-04 08:00:00', 3, 3), -- Vol 3: Boeing 787 sur trajet 3
('2026-02-05 09:30:00', '2026-02-05 13:00:00', 4, 4), -- Vol 4: ATR 72 sur trajet 4
('2026-01-12 12:00:00', '2026-01-12 13:30:00', 4, 5), -- Vol 5: ATR 72 sur trajet 5
('2026-02-10 07:00:00', '2026-02-10 09:00:00', 4, 5), -- Vol 6: ATR 72 sur trajet 5
('2026-01-20 06:30:00', '2026-01-20 08:00:00', 5, 5); -- Vol 7: Embraer E175 sur trajet 5

-- Classes de place
INSERT INTO classe_place (libelle) VALUES
('Economique'),
('Business'),
('Premium'),
('Premiere');

-- Prix et capacités par classe pour chaque vol (prix_vol)
-- Vol 1 (Boeing 737, capacite 180)
INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places) VALUES
(1, 1, 250000, 120), -- Eco
(1, 2, 500000, 40),  -- Business
(1, 3, 800000, 20);  -- Premium

-- Vol 2 (Airbus A320, capacite 220)
INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places) VALUES
(2, 1, 300000, 160),
(2, 2, 600000, 40),
(2, 3, 900000, 20);

-- Vol 3 (Boeing 787, capacite 300)
INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places) VALUES
(3, 1, 350000, 220),
(3, 2, 700000, 60),
(3, 3, 1100000, 20);

-- Vol 4 (ATR 72, capacite 150)
INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places) VALUES
(4, 1, 200000, 110),
(4, 2, 400000, 30),
(4, 3, 700000, 10);

-- Vol 5 (ATR 72, capacite 150)
INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places) VALUES
(5, 1, 180000, 110),
(5, 2, 380000, 30),
(5, 3, 650000, 10);

-- Vol 6 (ATR 72, capacite 150)
INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places) VALUES
(6, 1, 200000, 110),
(6, 2, 400000, 30),
(6, 3, 700000, 10);

-- =====================================================
-- Vol Antananarivo (TNR) -> Nosy Be (NOS)
-- Avion de 120 places avec 3 classes
-- =====================================================

-- Prix par classe pour le vol TNR -> NOS (Id_vol = 7)
-- Première (id 4): 30 places à 1 200 000 Ar
-- Premium (id 3): 40 places à 1 000 000 Ar  
-- Économique (id 1): 50 places à 700 000 Ar
INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places) VALUES
(7, 4, 1200000, 30),  -- Première
(7, 3, 1000000, 40),  -- Premium
(7, 1, 700000, 50);   -- Économique


-- INSERT INTO vol_avion (Id_avion, Id_vol, numero) VALUES
-- (1, 1, 101),
-- (2, 2, 202),
-- (3, 3, 303),
-- (4, 4, 404),
-- (4, 5, 505);

-- Nouveau vol: Tana (TNR) -> Nosy Be (NOS)
-- Utilise trajet id 5 (TNR -> NOS) et avion id 4 (ATR 72)
INSERT INTO vol (date_heure_depart, date_heure_arrive, Id_avion, Id_trajet) VALUES
('2026-02-10 07:00:00', '2026-02-10 09:00:00', 4, 5);

-- Prix pour ce vol (Première classe et Économique)
INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places)
SELECT v.Id_vol, 3, 1200000, 30 FROM vol v
WHERE v.date_heure_depart = '2026-02-10 07:00:00' AND v.Id_trajet = 5;

INSERT INTO prix_vol (Id_vol, Id_classe_place, prix, nbr_places)
SELECT v.Id_vol, 1, 700000, 120 FROM vol v
WHERE v.date_heure_depart = '2026-02-10 07:00:00' AND v.Id_trajet = 5;

-- INSERT INTO achat_places (nom_client, nombre_place, Id_vol) VALUES
-- ('Rakoto Jean', '2', 1),
-- ('Rabe Marie', '1', 1),
-- ('Smith John', '3', 2),
-- ('Dupont Alice', '2', 3),
-- ('Andry Paul', '4', 4);