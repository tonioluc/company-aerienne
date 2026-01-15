INSERT INTO aeroport (code_IATA, ville) VALUES
('TNR', 'Antananarivo'),
('NOS', 'Nosy Be');

INSERT INTO avion (capacite, modele) VALUES
(120, 'Embraer E175');

-- Trajets (sans numero_vol selon base.sql)
INSERT INTO trajet (Id_aeroport_depart, Id_aeroport_arrive) VALUES
(1, 2); -- TNR -> NOS

-- Vols (sans prix_place, selon base.sql)
INSERT INTO vol (date_heure_depart, date_heure_arrive, Id_avion, Id_trajet) VALUES
('2026-01-20 06:30:00', '2026-01-20 08:00:00', 1, 1); -- Vol 1: Embraer E175 sur trajet 5

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
(1, 3, 1200000, 30),  -- Première
(1, 2, 1000000, 40),  -- Premium
(1, 1, 700000, 50);   -- Économique
