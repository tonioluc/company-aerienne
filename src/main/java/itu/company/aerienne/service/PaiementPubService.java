package itu.company.aerienne.service;

import itu.company.aerienne.dto.PaiementPubDto;
import itu.company.aerienne.model.PaiementPub;
import itu.company.aerienne.model.Societe;
import itu.company.aerienne.repository.PaiementPubRepository;
import itu.company.aerienne.repository.SocieteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementPubService {

    @Autowired
    private PaiementPubRepository paiementPubRepository;

    @Autowired
    private SocieteRepository societeRepository;

    public List<PaiementPub> findAll() {
        return paiementPubRepository.findAll();
    }

    public Optional<PaiementPub> findById(Integer id) {
        return paiementPubRepository.findById(id);
    }

    public PaiementPub save(PaiementPub paiementPub) {
        return paiementPubRepository.save(paiementPub);
    }

    public void deleteById(Integer id) {
        paiementPubRepository.deleteById(id);
    }

    public List<PaiementPub> findByIdSociete(Integer idSociete) {
        return paiementPubRepository.findByIdSociete(idSociete);
    }

    /**
     * Récupère le total des paiements pour une société.
     */
    public BigDecimal getTotalPaiementBySociete(Integer idSociete) {
        BigDecimal total = paiementPubRepository.getTotalPaiementBySociete(idSociete);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Récupère la liste des paiements avec les détails de la société.
     */
    public List<PaiementPubDto> getAllPaiementsWithDetails() {
        List<PaiementPubDto> result = new ArrayList<>();
        List<PaiementPub> paiements = paiementPubRepository.findAll();

        for (PaiementPub paiement : paiements) {
            PaiementPubDto dto = new PaiementPubDto();
            dto.setIdPaiementPub(paiement.getIdPaiementPub());
            dto.setDatePaiement(paiement.getDatePaiement());
            dto.setMontant(paiement.getMontant());
            dto.setIdSociete(paiement.getIdSociete());

            // Récupérer le nom de la société
            Societe societe = societeRepository.findById(paiement.getIdSociete()).orElse(null);
            dto.setNomSociete(societe != null ? societe.getNom() : "Société inconnue");

            result.add(dto);
        }

        return result;
    }
}
