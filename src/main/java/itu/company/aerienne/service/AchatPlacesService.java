package itu.company.aerienne.service;

import itu.company.aerienne.dto.ChiffreAffaireDto;
import itu.company.aerienne.model.AchatPlaces;
import itu.company.aerienne.repository.AchatPlacesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AchatPlacesService {

    @Autowired
    private AchatPlacesRepository repository;

    public List<AchatPlaces> findAll() {
        return repository.findAll();
    }

    public Optional<AchatPlaces> findById(Integer id) {
        return repository.findById(id);
    }

    public AchatPlaces save(AchatPlaces achatPlaces) {
        return repository.save(achatPlaces);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    public List<AchatPlaces> findByIdVol(Integer idVol) {
        return repository.findByIdVol(idVol);
    }

    /**
     * Retourne le nombre total de places déjà achetées pour un vol donné.
     */
    public Integer getTotalPlacesAcheteesByVol(Integer idVol) {
        return repository.getTotalPlacesAcheteesByVol(idVol);
    }

    /**
     * Retourne le chiffre d'affaires pour une date donnée.
     */
    public ChiffreAffaireDto getChiffreAffairesByDate(LocalDate date) {
        Integer totalPlaces = repository.getTotalPlacesVenduesByDate(date);
        BigDecimal chiffreAffaires = repository.getChiffreAffairesByDate(date);
        return new ChiffreAffaireDto(totalPlaces, chiffreAffaires);
    }
}
