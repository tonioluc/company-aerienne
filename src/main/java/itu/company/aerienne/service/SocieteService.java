package itu.company.aerienne.service;

import itu.company.aerienne.model.Societe;
import itu.company.aerienne.repository.SocieteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocieteService {

    @Autowired
    private SocieteRepository repository;

    public List<Societe> findAll() {
        return repository.findAll();
    }

    public Optional<Societe> findById(Integer id) {
        return repository.findById(id);
    }

    public Societe save(Societe Societe) {
        return repository.save(Societe);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
