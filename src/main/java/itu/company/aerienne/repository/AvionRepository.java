package itu.company.aerienne.repository;

import itu.company.aerienne.model.Avion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvionRepository extends JpaRepository<Avion, Integer> {
}
