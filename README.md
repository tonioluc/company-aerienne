# aerienne

Instructions pour installer et exécuter le projet Spring Boot `aerienne`.

## Prérequis
- Apache Maven 3.8.7
- Java 17.0.9 (JDK)
- PostgreSQL (service local)

Vérifiez les versions installées :

```bash
mvn -v
java -version
psql --version
```

## Initialisation de la base de données
Les scripts SQL se trouvent dans le dossier `database/` :

- `database/base.sql` : crée la base de données `aerienne` et la table `avions`.
- `database/data.sql` : insère des données de test dans `avions`.

Exemples de commandes pour initialiser (utilisateur `postgres`) :
```bash
# devenir utilisateur postgres
sudo -i -u postgres

# lancer psql
psql
```

```sql
-- dans psql
\i /chemin/vers/repo/database/base.sql
\c aerienne
\i /chemin/vers/repo/database/data.sql
```
## Configuration de l'application
Si nécessaire modifiez le mot de passe et l'utilisateur PostgreSQL dans :

- `src/main/resources/application.properties`

Recherchez et adaptez les propriétés `spring.datasource.username` et `spring.datasource.password`.

Exemple :

```
spring.datasource.url=jdbc:postgresql://localhost:5432/aerienne
spring.datasource.username=postgres
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

## Lancer l'application
À la racine du projet :

```bash
mvn spring-boot:run
```

Remarque : la première exécution télécharge les dépendances depuis Internet.

## Tester l'interface
Ouvrez dans votre navigateur :

```
http://localhost:8080/avions
```

Autres URLs utiles :

- Créer un avion : `http://localhost:8080/avions/new`
- Éditer un avion (ex. id=1) : `http://localhost:8080/avions/1/edit`

Si vous avez modifié le port (`server.port`) dans `application.properties`, remplacez `8080` par le port choisi.

## Dépannage rapide
- Erreur de connexion : vérifiez `spring.datasource.*` et que PostgreSQL écoute sur `localhost:5432`.
- Table manquante : assurez-vous d'avoir exécuté `database/base.sql`.
- Problèmes de dépendances : assurez-vous d'avoir une connexion Internet pour le premier `mvn`.

## Notes
- Le fichier `application.properties` est configuré par défaut avec `spring.jpa.hibernate.ddl-auto=none` pour ne pas modifier la structure de la base existante.
- Les vues Thymeleaf sont disponibles dans `src/main/resources/templates/avions/`.

Si vous voulez, je peux ajouter un script `Makefile` ou des commandes d'initialisation automatisées.