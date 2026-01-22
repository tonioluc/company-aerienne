package itu.company.aerienne.repository;

import itu.company.aerienne.model.PaiementPub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaiementPubRepository extends JpaRepository<PaiementPub, Integer> {
    
    List<PaiementPub> findByIdSociete(Integer idSociete);
    
    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM PaiementPub p WHERE p.idSociete = :idSociete")
    BigDecimal getTotalPaiementBySociete(@Param("idSociete") Integer idSociete);
}
