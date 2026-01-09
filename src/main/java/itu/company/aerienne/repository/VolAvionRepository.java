package itu.company.aerienne.repository;

import itu.company.aerienne.model.VolAvion;
import itu.company.aerienne.model.VolAvionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolAvionRepository extends JpaRepository<VolAvion, VolAvionId> {
    List<VolAvion> findByIdVol(Integer idVol);
}
