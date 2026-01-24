package itu.company.aerienne.service;

import itu.company.aerienne.dto.PaiementFactureDto;
import itu.company.aerienne.model.FactureMere;
import itu.company.aerienne.model.PaiementFacture;
import itu.company.aerienne.model.Societe;
import itu.company.aerienne.repository.FactureMereRepository;
import itu.company.aerienne.repository.PaiementFactureRepository;
import itu.company.aerienne.repository.SocieteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementFactureService {

    @Autowired
    private PaiementFactureRepository paiementFactureRepository;

    @Autowired
    private FactureMereRepository factureMereRepository;

    @Autowired
    private SocieteRepository societeRepository;

    public List<PaiementFacture> findAll() {
        return paiementFactureRepository.findAll();
    }

    public Optional<PaiementFacture> findById(Integer id) {
        return paiementFactureRepository.findById(id);
    }

    /**
     * Enregistre un paiement et met à jour le montant payé de la facture
     */
    @Transactional
    public PaiementFacture save(PaiementFacture paiement) {
        PaiementFacture saved = paiementFactureRepository.save(paiement);
        
        // Mettre à jour le montant_paye dans facture_mere
        FactureMere facture = factureMereRepository.findById(paiement.getIdFactureMere()).orElse(null);
        if (facture != null) {
            BigDecimal montantActuel = facture.getMontantPaye() != null ? facture.getMontantPaye() : BigDecimal.ZERO;
            facture.setMontantPaye(montantActuel.add(paiement.getMontantPaye()));
            factureMereRepository.save(facture);
        }
        
        return saved;
    }

    @Transactional
    public void deleteById(Integer id) {
        PaiementFacture paiement = paiementFactureRepository.findById(id).orElse(null);
        if (paiement != null) {
            // Soustraire le montant du paiement de la facture
            FactureMere facture = factureMereRepository.findById(paiement.getIdFactureMere()).orElse(null);
            if (facture != null) {
                BigDecimal montantActuel = facture.getMontantPaye() != null ? facture.getMontantPaye() : BigDecimal.ZERO;
                facture.setMontantPaye(montantActuel.subtract(paiement.getMontantPaye()));
                factureMereRepository.save(facture);
            }
            paiementFactureRepository.deleteById(id);
        }
    }

    public List<PaiementFacture> findByIdFactureMere(Integer idFactureMere) {
        return paiementFactureRepository.findByIdFactureMere(idFactureMere);
    }

    public BigDecimal getTotalPaiementByFacture(Integer idFactureMere) {
        BigDecimal total = paiementFactureRepository.getTotalPaiementByFacture(idFactureMere);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * Récupère la liste des paiements avec les détails de la facture et de la société
     */
    public List<PaiementFactureDto> getAllPaiementsWithDetails() {
        List<PaiementFactureDto> result = new ArrayList<>();
        List<PaiementFacture> paiements = paiementFactureRepository.findAll();

        for (PaiementFacture paiement : paiements) {
            PaiementFactureDto dto = new PaiementFactureDto();
            dto.setIdPaiementFacture(paiement.getIdPaiementFacture());
            dto.setIdFactureMere(paiement.getIdFactureMere());
            dto.setDatePaiement(paiement.getDatePaiement());
            dto.setMontantPaye(paiement.getMontantPaye());

            // Récupérer les infos de la facture
            FactureMere facture = factureMereRepository.findById(paiement.getIdFactureMere()).orElse(null);
            if (facture != null) {
                dto.setMois(facture.getMois());
                dto.setAnnee(facture.getAnnee());
                dto.setMontantTotalFacture(facture.getMontantTotal());
                dto.setMontantRestantFacture(facture.getMontantRestant());

                // Récupérer le nom de la société
                Societe societe = societeRepository.findById(facture.getIdSociete()).orElse(null);
                dto.setNomSociete(societe != null ? societe.getNom() : "Société inconnue");
            }

            result.add(dto);
        }

        return result;
    }
}
