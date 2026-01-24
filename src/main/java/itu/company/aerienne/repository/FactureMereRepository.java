package itu.company.aerienne.repository;

import itu.company.aerienne.model.FactureMere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactureMereRepository extends JpaRepository<FactureMere, Integer> {
    
    List<FactureMere> findByIdSociete(Integer idSociete);
    
    // Factures non soldées d'une société (montant restant > 0)
    @Query("SELECT f FROM FactureMere f WHERE f.idSociete = :idSociete AND (f.montantTotal - COALESCE(f.montantPaye, 0)) > 0")
    List<FactureMere> findFacturesNonSoldeesBySociete(@Param("idSociete") Integer idSociete);
    
    // Toutes les factures non soldées
    @Query("SELECT f FROM FactureMere f WHERE (f.montantTotal - COALESCE(f.montantPaye, 0)) > 0")
    List<FactureMere> findAllFacturesNonSoldees();
    
    // Trouver une facture par société, mois et année
    @Query("SELECT f FROM FactureMere f WHERE f.idSociete = :idSociete AND f.mois = :mois AND f.annee = :annee")
    FactureMere findByIdSocieteAndMoisAndAnnee(@Param("idSociete") Integer idSociete, 
                                               @Param("mois") Integer mois, 
                                               @Param("annee") Integer annee);
}