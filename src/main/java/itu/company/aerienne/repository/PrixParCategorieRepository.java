package itu.company.aerienne.repository;

import itu.company.aerienne.model.PrixParCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PrixParCategorieRepository extends JpaRepository<PrixParCategorie, Integer> {
    
    /**
     * Recherche un prix spécial par catégorie de client et classe de place.
     * 
     * @param idCategoriePersonne ID de la catégorie du client
     * @param idClassePlace ID de la classe de place
     * @return Le prix spécial si trouvé
     */
    @Query("SELECT p FROM PrixParCategorie p WHERE p.idCategoriePersonne = :idCategoriePersonne AND p.idClassePlace = :idClassePlace")
    Optional<PrixParCategorie> findByIdCategoriePersonneAndIdClassePlace(
            @Param("idCategoriePersonne") Integer idCategoriePersonne, 
            @Param("idClassePlace") Integer idClassePlace);
}
