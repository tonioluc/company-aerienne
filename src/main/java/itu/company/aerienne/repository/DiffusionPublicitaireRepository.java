
package itu.company.aerienne.repository;

import itu.company.aerienne.model.DiffusionPublicitaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiffusionPublicitaireRepository extends JpaRepository<DiffusionPublicitaire, Integer> {
    
    List<DiffusionPublicitaire> findByMoisAndAnnee(Integer mois, Integer annee);
    
    List<DiffusionPublicitaire> findByIdVol(Integer idVol);
    
    @Query("SELECT COALESCE(SUM(d.nombreDiffusion), 0) FROM DiffusionPublicitaire d WHERE d.idVol = :idVol")
    Integer getTotalDiffusionsByVol(@Param("idVol") Integer idVol);
}