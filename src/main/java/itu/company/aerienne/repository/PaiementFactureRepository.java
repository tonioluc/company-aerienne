package itu.company.aerienne.repository;

import itu.company.aerienne.model.PaiementFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaiementFactureRepository extends JpaRepository<PaiementFacture, Integer> {
    
    List<PaiementFacture> findByIdFactureMere(Integer idFactureMere);
    
    @Query("SELECT COALESCE(SUM(p.montantPaye), 0) FROM PaiementFacture p WHERE p.idFactureMere = :idFactureMere")
    BigDecimal getTotalPaiementByFacture(@Param("idFactureMere") Integer idFactureMere);
}