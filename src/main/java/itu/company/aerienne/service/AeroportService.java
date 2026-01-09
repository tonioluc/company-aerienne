package itu.company.aerienne.service;

import itu.company.aerienne.model.Aeroport;
import itu.company.aerienne.repository.AeroportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AeroportService {

    @Autowired
    private AeroportRepository repository;

    public List<Aeroport> findAll() {
        return repository.findAll();
    }

    public Optional<Aeroport> findById(Integer id) {
        return repository.findById(id);
    }

    public Aeroport save(Aeroport Aeroport) {
        return repository.save(Aeroport);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
