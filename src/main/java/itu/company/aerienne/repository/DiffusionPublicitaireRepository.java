
package itu.company.aerienne.repository;

import itu.company.aerienne.model.DiffusionPublicitaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiffusionPublicitaireRepository extends JpaRepository<DiffusionPublicitaire, Integer> {
    
    List<DiffusionPublicitaire> findByMoisAndAnnee(Integer mois, Integer annee);
}