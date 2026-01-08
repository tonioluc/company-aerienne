package itu.company.aerienne.service;

import itu.company.aerienne.model.Avion;
import itu.company.aerienne.repository.AvionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvionService {

    @Autowired
    private AvionRepository repository;

    public List<Avion> findAll() {
        return repository.findAll();
    }

    public Optional<Avion> findById(Integer id) {
        return repository.findById(id);
    }

    public Avion save(Avion avion) {
        return repository.save(avion);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
