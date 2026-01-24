package itu.company.aerienne.repository;

import itu.company.aerienne.model.PrixVol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrixVolRepository extends JpaRepository<PrixVol, Integer> {
    List<PrixVol> findByIdVol(Integer idVol);
    
    @Query("SELECT p FROM PrixVol p WHERE p.idVol = :idVol AND p.idClassePlace = :idClassePlace")
    Optional<PrixVol> findByIdVolAndIdClassePlace(@Param("idVol") Integer idVol, @Param("idClassePlace") Integer idClassePlace);
}