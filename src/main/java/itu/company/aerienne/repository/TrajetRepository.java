package itu.company.aerienne.repository;

import itu.company.aerienne.model.Trajet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrajetRepository extends JpaRepository<Trajet, Integer> {
}
