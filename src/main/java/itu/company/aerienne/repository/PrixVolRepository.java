package itu.company.aerienne.repository;

import itu.company.aerienne.model.PrixVol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrixVolRepository extends JpaRepository<PrixVol, Integer> {
    List<PrixVol> findByIdVol(Integer idVol);
}