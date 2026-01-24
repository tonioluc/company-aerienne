package itu.company.aerienne.service;

import itu.company.aerienne.dto.FactureMereDto;
import itu.company.aerienne.model.FactureMere;
import itu.company.aerienne.model.Societe;
import itu.company.aerienne.repository.FactureMereRepository;
import itu.company.aerienne.repository.SocieteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FactureMereService {

    @Autowired
    private FactureMereRepository factureMereRepository;

    @Autowired
    private SocieteRepository societeRepository;

    public List<FactureMere> findAll() {
        return factureMereRepository.findAll();
    }

    public Optional<FactureMere> findById(Integer id) {
        return factureMereRepository.findById(id);
    }

    public FactureMere save(FactureMere factureMere) {
        return factureMereRepository.save(factureMere);
    }

    public void deleteById(Integer id) {
        factureMereRepository.deleteById(id);
    }

    public List<FactureMere> findByIdSociete(Integer idSociete) {
        return factureMereRepository.findByIdSociete(idSociete);
    }

    /**
     * Récupère les factures non soldées d'une société
     */
    public List<FactureMere> findFacturesNonSoldeesBySociete(Integer idSociete) {
        return factureMereRepository.findFacturesNonSoldeesBySociete(idSociete);
    }

    /**
     * Récupère toutes les factures non soldées
     */
    public List<FactureMere> findAllFacturesNonSoldees() {
        return factureMereRepository.findAllFacturesNonSoldees();
    }

    /**
     * Convertit une FactureMere en DTO avec les détails
     */
    public FactureMereDto toDto(FactureMere facture) {
        FactureMereDto dto = new FactureMereDto();
        dto.setIdFactureMere(facture.getIdFactureMere());
        dto.setIdSociete(facture.getIdSociete());
        dto.setMois(facture.getMois());
        dto.setAnnee(facture.getAnnee());
        dto.setMontantTotal(facture.getMontantTotal() != null ? facture.getMontantTotal() : BigDecimal.ZERO);
        dto.setMontantPaye(facture.getMontantPaye() != null ? facture.getMontantPaye() : BigDecimal.ZERO);
        dto.setMontantRestant(facture.getMontantRestant());

        // Récupérer le nom de la société
        Societe societe = societeRepository.findById(facture.getIdSociete()).orElse(null);
        dto.setNomSociete(societe != null ? societe.getNom() : "Société inconnue");
        
        dto.generateLibelle();
        return dto;
    }

    /**
     * Récupère les factures non soldées d'une société sous forme de DTO
     */
    public List<FactureMereDto> getFacturesNonSoldeesDtoBySociete(Integer idSociete) {
        List<FactureMereDto> result = new ArrayList<>();
        List<FactureMere> factures = findFacturesNonSoldeesBySociete(idSociete);
        
        for (FactureMere facture : factures) {
            result.add(toDto(facture));
        }
        return result;
    }

    /**
     * Récupère toutes les factures sous forme de DTO
     */
    public List<FactureMereDto> getAllFacturesDto() {
        List<FactureMereDto> result = new ArrayList<>();
        List<FactureMere> factures = findAll();
        
        for (FactureMere facture : factures) {
            result.add(toDto(facture));
        }
        return result;
    }

    /**
     * Met à jour le montant payé d'une facture après un paiement
     */
    public void updateMontantPaye(Integer idFactureMere, BigDecimal montantPaiement) {
        FactureMere facture = factureMereRepository.findById(idFactureMere).orElse(null);
        if (facture != null) {
            BigDecimal montantActuel = facture.getMontantPaye() != null ? facture.getMontantPaye() : BigDecimal.ZERO;
            facture.setMontantPaye(montantActuel.add(montantPaiement));
            factureMereRepository.save(facture);
        }
    }
}
