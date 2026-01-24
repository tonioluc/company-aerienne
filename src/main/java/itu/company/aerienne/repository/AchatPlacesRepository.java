package itu.company.aerienne.repository;

import itu.company.aerienne.model.AchatPlaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AchatPlacesRepository extends JpaRepository<AchatPlaces, Integer> {

       List<AchatPlaces> findByIdVol(Integer idVol);

       @Query("SELECT COALESCE(SUM(CAST(a.nombrePlace AS int)), 0) FROM AchatPlaces a WHERE a.idVol = :idVol")
       Integer getTotalPlacesAcheteesByVol(@Param("idVol") Integer idVol);

       // Méthodes de requête par date supprimées — obsolètes

       @Query("SELECT COALESCE(SUM(CAST(a.nombrePlace AS int)), 0) FROM AchatPlaces a WHERE a.idVol = :idVol AND a.idClassePlace = :idClassePlace")
       Integer getTotalPlacesAcheteesByVolAndClasse(@Param("idVol") Integer idVol,
                     @Param("idClassePlace") Integer idClassePlace);
}
