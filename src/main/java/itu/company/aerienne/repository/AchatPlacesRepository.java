package itu.company.aerienne.repository;

import itu.company.aerienne.model.AchatPlaces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AchatPlacesRepository extends JpaRepository<AchatPlaces, Integer> {

    List<AchatPlaces> findByIdVol(Integer idVol);

    @Query("SELECT COALESCE(SUM(CAST(a.nombrePlace AS int)), 0) FROM AchatPlaces a WHERE a.idVol = :idVol")
    Integer getTotalPlacesAcheteesByVol(@Param("idVol") Integer idVol);

    @Query("SELECT COALESCE(SUM(CAST(a.nombrePlace AS int)), 0) FROM AchatPlaces a " +
           "JOIN Vol v ON a.idVol = v.idVol " +
           "WHERE CAST(v.dateHeureDepart AS LocalDate) = :date")
    Integer getTotalPlacesVenduesByDate(@Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(CAST(a.nombrePlace AS int) * v.prixPlace), 0) FROM AchatPlaces a " +
           "JOIN Vol v ON a.idVol = v.idVol " +
           "WHERE CAST(v.dateHeureDepart AS LocalDate) = :date")
    java.math.BigDecimal getChiffreAffairesByDate(@Param("date") LocalDate date);
}
