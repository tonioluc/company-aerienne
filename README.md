# aerienne — Guide rapide

Prérequis
- Java 17, Maven, PostgreSQL (local).

Initialiser la base (en tant que `postgres`):
```sql
\i /chemin/vers/repo/database/base.sql
\i /chemin/vers/repo/database/data.sql
```

Configurer la connexion DB dans `src/main/resources/application.properties`:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/aerienne
spring.datasource.username=postgres
spring.datasource.password=VOTRE_MOT_DE_PASSE
```

Démarrer l'application (à la racine du projet):
```bash
mvn spring-boot:run
```

Accéder à l'interface: `http://localhost:8080`

Astuce dépannage
- Erreur de connexion → vérifier `spring.datasource.*` et que PostgreSQL tourne.
- Tables manquantes → exécuter `database/base.sql` puis `database/data.sql`.

Fichiers importants
- `database/` : scripts SQL
- `src/main/resources/application.properties` : configuration DB
- `src/main/resources/templates/` : vues Thymeleaf