package itu.company.aerienne.service;

import itu.company.aerienne.model.ModeleVol;
import itu.company.aerienne.repository.ModeleVolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelVolService {

    @Autowired
    private ModeleVolRepository repository;

    public List<ModeleVol> findAll() {
        return repository.findAll();
    }

    public Optional<ModeleVol> findById(Integer id) {
        return repository.findById(id);
    }

    public ModeleVol save(ModeleVol ModeleVol) {
        return repository.save(ModeleVol);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
