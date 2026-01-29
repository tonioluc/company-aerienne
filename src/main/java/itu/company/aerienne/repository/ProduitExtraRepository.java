package itu.company.aerienne.repository;

import itu.company.aerienne.model.ProduitExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduitExtraRepository extends JpaRepository<ProduitExtra, Integer> {
}
