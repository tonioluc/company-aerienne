-- Modifier le prix de la Première classe (id 4) à 1 200 000 Ar
UPDATE prix_vol SET prix = 1200000 WHERE Id_vol = 1 AND Id_classe_place = 3;

-- Modifier le prix de la classe Premium (id 3) à 1 000 000 Ar
UPDATE prix_vol SET prix = 1000000 WHERE Id_vol = 1 AND Id_classe_place = 2;

-- Modifier le prix de la classe Économique (id 1) à 700 000 Ar
UPDATE prix_vol SET prix = 800000 WHERE Id_vol = 1 AND Id_classe_place = 1;