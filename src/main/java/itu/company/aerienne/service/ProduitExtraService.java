package itu.company.aerienne.service;

import itu.company.aerienne.model.ProduitExtra;
import itu.company.aerienne.repository.ProduitExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitExtraService {

    @Autowired
    private ProduitExtraRepository repository;

    public List<ProduitExtra> findAll() {
        return repository.findAll();
    }

    public Optional<ProduitExtra> findById(Integer id) {
        return repository.findById(id);
    }

    public ProduitExtra save(ProduitExtra produitExtra) {
        return repository.save(produitExtra);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
