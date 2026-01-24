package itu.company.aerienne.repository;

import itu.company.aerienne.model.FactureDiffusion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FactureDiffusionRepository extends JpaRepository<FactureDiffusion, Integer> {
    
    List<FactureDiffusion> findByIdFactureMere(Integer idFactureMere);

    FactureDiffusion findByIdDiffusionPublicitaire(Integer idDiffusionPublicitaire);
}