package itu.company.aerienne.service;

import itu.company.aerienne.model.Trajet;
import itu.company.aerienne.repository.TrajetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrajetService {

    @Autowired
    private TrajetRepository repository;

    public List<Trajet> findAll() {
        return repository.findAll();
    }

    public Optional<Trajet> findById(Integer id) {
        return repository.findById(id);
    }

    public Trajet save(Trajet Trajet) {
        return repository.save(Trajet);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
