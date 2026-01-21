package itu.company.aerienne.repository;

import itu.company.aerienne.model.PrixParCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrixParCategorieRepository extends JpaRepository<PrixParCategorie, Integer> {
    
    /**
     * Recherche un prix spécial par vol, catégorie de client et classe de place.
     * 
     * @param idVol ID du vol
     * @param idCategoriePersonne ID de la catégorie du client
     * @param idClassePlace ID de la classe de place
     * @return Le prix spécial si trouvé
     */
    @Query("SELECT p FROM PrixParCategorie p WHERE p.idVol = :idVol AND p.idCategoriePersonne = :idCategoriePersonne AND p.idClassePlace = :idClassePlace")
    Optional<PrixParCategorie> findByIdVolAndIdCategoriePersonneAndIdClassePlace(
            @Param("idVol") Integer idVol,
            @Param("idCategoriePersonne") Integer idCategoriePersonne, 
            @Param("idClassePlace") Integer idClassePlace);
    
    /**
     * Recherche tous les prix par catégorie pour un vol donné.
     * 
     * @param idVol ID du vol
     * @return Liste des prix par catégorie pour ce vol
     */
    @Query("SELECT p FROM PrixParCategorie p WHERE p.idVol = :idVol")
    List<PrixParCategorie> findByIdVol(@Param("idVol") Integer idVol);
    
    /**
     * Recherche un prix spécial par catégorie de client et classe de place (ancienne méthode, déprécié).
     * Cette méthode ne filtre pas par vol et ne devrait plus être utilisée.
     * 
     * @param idCategoriePersonne ID de la catégorie du client
     * @param idClassePlace ID de la classe de place
     * @return Le prix spécial si trouvé
     * @deprecated Utiliser findByIdVolAndIdCategoriePersonneAndIdClassePlace à la place
     */
    @Deprecated
    @Query("SELECT p FROM PrixParCategorie p WHERE p.idCategoriePersonne = :idCategoriePersonne AND p.idClassePlace = :idClassePlace")
    Optional<PrixParCategorie> findByIdCategoriePersonneAndIdClassePlace(
            @Param("idCategoriePersonne") Integer idCategoriePersonne, 
            @Param("idClassePlace") Integer idClassePlace);
}
