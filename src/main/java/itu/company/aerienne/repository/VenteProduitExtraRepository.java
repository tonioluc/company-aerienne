package itu.company.aerienne.repository;

import itu.company.aerienne.model.VenteProduitExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenteProduitExtraRepository extends JpaRepository<VenteProduitExtra, Integer> {
    List<VenteProduitExtra> findByIdVol(Integer idVol);
}
