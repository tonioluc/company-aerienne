package itu.company.aerienne.service;

import itu.company.aerienne.dto.FactureDiffusionDto;
import itu.company.aerienne.model.*;
import itu.company.aerienne.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FactureDiffusionService {

    @Autowired
    private FactureDiffusionRepository factureDiffusionRepository;

    @Autowired
    private DiffusionPublicitaireRepository diffusionRepository;

    @Autowired
    private SocieteRepository societeRepository;

    @Autowired
    private VolRepository volRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private AeroportRepository aeroportRepository;

    public List<FactureDiffusion> findAll() {
        return factureDiffusionRepository.findAll();
    }

    public List<FactureDiffusion> findByIdFactureMere(Integer idFactureMere) {
        return factureDiffusionRepository.findByIdFactureMere(idFactureMere);
    }

    /**
     * Récupère les détails des factures diffusion pour une facture mère
     */
    public List<FactureDiffusionDto> getFactureDiffusionDetails(Integer idFactureMere) {
        List<FactureDiffusionDto> result = new ArrayList<>();
        List<FactureDiffusion> factures = findByIdFactureMere(idFactureMere);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (FactureDiffusion fd : factures) {
            FactureDiffusionDto dto = new FactureDiffusionDto();
            dto.setIdFactureDiffusion(fd.getIdFactureDiffusion());
            dto.setIdFactureMere(fd.getIdFactureMere());
            dto.setIdDiffusionPublicitaire(fd.getIdDiffusionPublicitaire());
            dto.setMontant(fd.getMontant());

            // Récupérer les infos de la diffusion
            DiffusionPublicitaire diffusion = diffusionRepository.findById(fd.getIdDiffusionPublicitaire()).orElse(null);
            if (diffusion != null) {
                dto.setNombreDiffusion(diffusion.getNombreDiffusion());
                dto.setMois(diffusion.getMois());
                dto.setAnnee(diffusion.getAnnee());

                // Récupérer le nom de la société
                Societe societe = societeRepository.findById(diffusion.getIdSociete()).orElse(null);
                dto.setNomSociete(societe != null ? societe.getNom() : "N/A");

                // Récupérer les infos du vol
                Vol vol = volRepository.findById(diffusion.getIdVol()).orElse(null);
                if (vol != null) {
                    Trajet trajet = trajetRepository.findById(vol.getIdTrajet()).orElse(null);
                    if (trajet != null) {
                        Aeroport depart = aeroportRepository.findById(trajet.getIdAeroportDepart()).orElse(null);
                        Aeroport arrivee = aeroportRepository.findById(trajet.getIdAeroportArrive()).orElse(null);
                        String departCode = depart != null ? depart.getCodeIATA() : "?";
                        String arriveeCode = arrivee != null ? arrivee.getCodeIATA() : "?";
                        String dateVol = vol.getDateHeureDepart() != null ? vol.getDateHeureDepart().format(formatter) : "?";
                        dto.setInfoVol(departCode + " → " + arriveeCode + " - " + dateVol);
                    }
                }
            }

            result.add(dto);
        }

        return result;
    }
}
