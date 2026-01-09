package itu.company.aerienne.service;

import itu.company.aerienne.model.VolAvion;
import itu.company.aerienne.model.VolAvionId;
import itu.company.aerienne.repository.VolAvionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolAvionService {

    @Autowired
    private VolAvionRepository repository;

    public List<VolAvion> findAll() {
        return repository.findAll();
    }

    public Optional<VolAvion> findById(VolAvionId id) {
        return repository.findById(id);
    }

    public VolAvion save(VolAvion volAvion) {
        return repository.save(volAvion);
    }

    public void deleteById(VolAvionId id) {
        repository.deleteById(id);
    }

    public void deleteByIds(Integer idAvion, Integer idVol) {
        repository.deleteById(new VolAvionId(idAvion, idVol));
    }
}
