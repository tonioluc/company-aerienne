-- Modifier le prix de la Première classe (id 4) à 1 200 000 Ar
UPDATE prix_vol SET prix = 1200000 WHERE Id_vol = 1 AND Id_classe_place = 3;

-- Modifier le prix de la classe Premium (id 3) à 1 000 000 Ar
UPDATE prix_vol SET prix = 1000000 WHERE Id_vol = 1 AND Id_classe_place = 2;

-- Modifier le prix de la classe Économique (id 1) à 700 000 Ar
UPDATE prix_vol SET prix = 700000 WHERE Id_vol = 1 AND Id_classe_place = 1;


-- =====================================================
-- Scripts de mise à jour des prix par catégorie
-- Utilise des sous-requêtes pour éviter de dépendre des IDs
-- Exécuter après les INSERT ci-dessus
-- =====================================================

-- Mettre à jour ENFANT (prix fixe en priorité, pourcentage NULL)
UPDATE prix_par_categorie SET prix = 500000, pourcentage = NULL
WHERE Id_categorie_personne = (SELECT Id_categorie_client FROM categorie_client WHERE libelle = 'Enfant')
	AND Id_classe_place = (SELECT Id_classe_place FROM classe_place WHERE libelle = 'Economique');

UPDATE prix_par_categorie SET prix = 800000, pourcentage = NULL
WHERE Id_categorie_personne = (SELECT Id_categorie_client FROM categorie_client WHERE libelle = 'Enfant')
	AND Id_classe_place = (SELECT Id_classe_place FROM classe_place WHERE libelle = 'Premium');

UPDATE prix_par_categorie SET prix = 1000000, pourcentage = NULL
WHERE Id_categorie_personne = (SELECT Id_categorie_client FROM categorie_client WHERE libelle = 'Enfant')
	AND Id_classe_place = (SELECT Id_classe_place FROM classe_place WHERE libelle = 'Premiere');

-- Mettre à jour BEBE (prix NULL, calcul via pourcentage)
UPDATE prix_par_categorie SET prix = NULL, pourcentage = 10
WHERE Id_categorie_personne = (SELECT Id_categorie_client FROM categorie_client WHERE libelle = 'Bébé')
	AND Id_classe_place = (SELECT Id_classe_place FROM classe_place WHERE libelle = 'Economique');

UPDATE prix_par_categorie SET prix = NULL, pourcentage = 10
WHERE Id_categorie_personne = (SELECT Id_categorie_client FROM categorie_client WHERE libelle = 'Bébé')
	AND Id_classe_place = (SELECT Id_classe_place FROM classe_place WHERE libelle = 'Premium');

UPDATE prix_par_categorie SET prix = NULL, pourcentage = 10
WHERE Id_categorie_personne = (SELECT Id_categorie_client FROM categorie_client WHERE libelle = 'Bébé')
	AND Id_classe_place = (SELECT Id_classe_place FROM classe_place WHERE libelle = 'Premiere');
