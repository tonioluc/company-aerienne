INSERT INTO aeroport (code_IATA, ville) VALUES
('TNR', 'Antananarivo'),
('DZA', 'Dzaoudzi'),
('RUN', 'Saint-Denis'),
('CDG', 'Paris'),
('JNB', 'Johannesburg'),
('NOS', 'Nosy Be');

INSERT INTO avion (capacité, modele) VALUES
(180, 'Boeing 737'),
(220, 'Airbus A320'),
(300, 'Boeing 787'),
(150, 'ATR 72');

INSERT INTO modele_vol (numero_vol, Id_aeroport_depart, Id_aeroport_arrive) VALUES
('MD001', 1, 2),
('MD002', 1, 3),
('MD003', 4, 1),
('MD004', 1, 5),
('MD005', 1, 6);

INSERT INTO vol (date_heure_depart, date_heure_arrive, prix_place, Id_modele_vol) VALUES
('2026-02-01 08:00:00', '2026-02-01 10:30:00', 250000, 1),
('2026-02-02 14:00:00', '2026-02-02 16:45:00', 350000, 2),
('2026-02-03 21:00:00', '2026-02-04 08:00:00', 1500000, 3),
('2026-02-05 09:30:00', '2026-02-05 13:00:00', 800000, 4),
('2026-01-12 12:00:00', '2026-01-12 13:30:00', 450000, 5);


INSERT INTO vol_avion (Id_avion, Id_vol, numero) VALUES
(1, 1, 101),
(2, 2, 202),
(3, 3, 303),
(4, 4, 404),
(4, 5, 505);

-- INSERT INTO achat_places (nom_client, nombre_place, Id_vol) VALUES
-- ('Rakoto Jean', '2', 1),
-- ('Rabe Marie', '1', 1),
-- ('Smith John', '3', 2),
-- ('Dupont Alice', '2', 3),
-- ('Andry Paul', '4', 4);