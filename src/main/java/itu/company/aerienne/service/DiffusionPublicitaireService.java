package itu.company.aerienne.service;

import itu.company.aerienne.dto.DiffusionChiffreAffaireDto;
import itu.company.aerienne.dto.DiffusionFormDto;
import itu.company.aerienne.model.Aeroport;
import itu.company.aerienne.model.CoutDiffusion;
import itu.company.aerienne.model.DiffusionPublicitaire;
import itu.company.aerienne.model.Societe;
import itu.company.aerienne.model.Trajet;
import itu.company.aerienne.model.Vol;
import itu.company.aerienne.repository.AeroportRepository;
import itu.company.aerienne.repository.CoutDiffusionRepository;
import itu.company.aerienne.repository.DiffusionPublicitaireRepository;
import itu.company.aerienne.repository.PaiementPubRepository;
import itu.company.aerienne.repository.SocieteRepository;
import itu.company.aerienne.repository.TrajetRepository;
import itu.company.aerienne.repository.VolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DiffusionPublicitaireService {

    @Autowired
    private DiffusionPublicitaireRepository diffusionRepository;

    @Autowired
    private VolRepository volRepository;

    @Autowired
    private SocieteRepository societeRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private AeroportRepository aeroportRepository;

    @Autowired
    private CoutDiffusionRepository coutDiffusionRepository;

    @Autowired
    private PaiementPubRepository paiementPubRepository;

    public List<DiffusionPublicitaire> findAll() {
        return diffusionRepository.findAll();
    }

    public Optional<DiffusionPublicitaire> findById(Integer id) {
        return diffusionRepository.findById(id);
    }

    public DiffusionPublicitaire save(DiffusionPublicitaire diffusion) {
        return diffusionRepository.save(diffusion);
    }

    /**
     * Ajoute une diffusion publicitaire à partir du formulaire.
     * Le mois et l'année sont extraits automatiquement de la date de départ du vol.
     */
    public DiffusionPublicitaire ajouterDiffusion(DiffusionFormDto formDto) {
        // Récupérer le vol pour obtenir la date de départ
        Vol vol = volRepository.findById(formDto.getVol())
                .orElseThrow(() -> new RuntimeException("Vol non trouvé avec l'id: " + formDto.getVol()));

        LocalDateTime dateHeureDepart = vol.getDateHeureDepart();

        // Créer la diffusion avec mois et année dénormalisés
        DiffusionPublicitaire diffusion = new DiffusionPublicitaire();
        diffusion.setIdSociete(formDto.getSociete());
        diffusion.setIdVol(formDto.getVol());
        diffusion.setNombreDiffusion(formDto.getNombreDiffusion());

        // Dénormalisation: mois = numéro du mois (1-12)
        diffusion.setMois(dateHeureDepart.getMonthValue());

        // Dénormalisation: année = année (ex: 2026)
        diffusion.setAnnee(dateHeureDepart.getYear());

        return diffusionRepository.save(diffusion);
    }

    /**
     * Récupère le chiffre d'affaires des diffusions pour un mois et une année donnés.
     */
    public List<DiffusionChiffreAffaireDto> getChiffreAffaireDiffusion(Integer mois, Integer annee) {
        List<DiffusionChiffreAffaireDto> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        // Récupérer le coût unitaire de diffusion
        BigDecimal coutUnitaire = getCoutUnitaire();

        // Récupérer les diffusions pour le mois et l'année donnés
        List<DiffusionPublicitaire> diffusions = diffusionRepository.findByMoisAndAnnee(mois, annee);

        // Map pour stocker le total des coûts par société
        Map<Integer, BigDecimal> totalCoutParSociete = new HashMap<>();

        for (DiffusionPublicitaire diffusion : diffusions) {
            DiffusionChiffreAffaireDto dto = new DiffusionChiffreAffaireDto();

            // Récupérer le vol
            Vol vol = volRepository.findById(diffusion.getIdVol()).orElse(null);
            if (vol != null) {
                // Construire la description du vol
                Trajet trajet = trajetRepository.findById(vol.getIdTrajet()).orElse(null);
                if (trajet != null) {
                    Aeroport aeroportDepart = aeroportRepository.findById(trajet.getIdAeroportDepart()).orElse(null);
                    Aeroport aeroportArrive = aeroportRepository.findById(trajet.getIdAeroportArrive()).orElse(null);

                    String villeDepart = aeroportDepart != null ? aeroportDepart.getVille() : "N/A";
                    String villeArrive = aeroportArrive != null ? aeroportArrive.getVille() : "N/A";
                    String dateDepart = vol.getDateHeureDepart() != null ? vol.getDateHeureDepart().format(formatter) : "N/A";
                    String dateArrive = vol.getDateHeureArrive() != null ? vol.getDateHeureArrive().format(formatter) : "N/A";

                    dto.setVolDescription(villeDepart + " " + dateDepart + " → " + villeArrive + " " + dateArrive);
                } else {
                    dto.setVolDescription("Vol #" + vol.getIdVol());
                }
            } else {
                dto.setVolDescription("Vol inconnu");
            }

            // Récupérer la société
            Integer idSociete = diffusion.getIdSociete();
            dto.setIdSociete(idSociete);
            Societe societe = societeRepository.findById(idSociete).orElse(null);
            dto.setNomSociete(societe != null ? societe.getNom() : "Société inconnue");

            // Nombre de diffusion
            dto.setNombreDiffusion(diffusion.getNombreDiffusion());

            // Calculer le total du coût = nombre_diffusion * cout_unitaire
            BigDecimal totalCout = coutUnitaire.multiply(BigDecimal.valueOf(diffusion.getNombreDiffusion() != null ? diffusion.getNombreDiffusion() : 0));
            dto.setTotalCout(totalCout);

            // Accumuler le coût par société
            totalCoutParSociete.merge(idSociete, totalCout, BigDecimal::add);

            result.add(dto);
        }

        // Calculer le montant payé et le reste à payer pour chaque société
        for (DiffusionChiffreAffaireDto dto : result) {
            Integer idSociete = dto.getIdSociete();
            
            // Récupérer le total payé par cette société
            BigDecimal totalPaye = paiementPubRepository.getTotalPaiementBySociete(idSociete);
            if (totalPaye == null) {
                totalPaye = BigDecimal.ZERO;
            }
            
            // Total des coûts pour cette société
            BigDecimal totalCoutSociete = totalCoutParSociete.getOrDefault(idSociete, BigDecimal.ZERO);
            
            dto.setMontantPaye(totalPaye);
            
            // Reste à payer = total coût société - total payé
            BigDecimal resteAPayer = totalCoutSociete.subtract(totalPaye);
            dto.setResteAPayer(resteAPayer.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : resteAPayer);
        }

        return result;
    }

    /**
     * Récupère le coût unitaire de diffusion (première entrée de la table cout_diffusion).
     */
    public BigDecimal getCoutUnitaire() {
        List<CoutDiffusion> couts = coutDiffusionRepository.findAll();
        if (!couts.isEmpty()) {
            return couts.get(0).getCoutUnitaire() != null ? couts.get(0).getCoutUnitaire() : BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    public void deleteById(Integer id) {
        diffusionRepository.deleteById(id);
    }
}
