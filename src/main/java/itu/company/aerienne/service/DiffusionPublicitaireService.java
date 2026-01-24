package itu.company.aerienne.service;

import itu.company.aerienne.dto.DiffusionChiffreAffaireDto;
import itu.company.aerienne.dto.DiffusionFormDto;
import itu.company.aerienne.model.Aeroport;
import itu.company.aerienne.model.CoutDiffusion;
import itu.company.aerienne.model.DiffusionPublicitaire;
import itu.company.aerienne.model.FactureDiffusion;
import itu.company.aerienne.model.FactureMere;
import itu.company.aerienne.model.Societe;
import itu.company.aerienne.model.Trajet;
import itu.company.aerienne.model.Vol;
import itu.company.aerienne.repository.AeroportRepository;
import itu.company.aerienne.repository.CoutDiffusionRepository;
import itu.company.aerienne.repository.DiffusionPublicitaireRepository;
import itu.company.aerienne.repository.FactureDiffusionRepository;
import itu.company.aerienne.repository.FactureMereRepository;
import itu.company.aerienne.repository.SocieteRepository;
import itu.company.aerienne.repository.TrajetRepository;
import itu.company.aerienne.repository.VolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    private FactureMereRepository factureMereRepository;

    @Autowired
    private FactureDiffusionRepository factureDiffusionRepository;

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
     * Crée ou met à jour automatiquement la facture_mere et facture_diffusion.
     */
    @Transactional
    public DiffusionPublicitaire ajouterDiffusion(DiffusionFormDto formDto) {
        // Récupérer le vol pour obtenir la date de départ
        Vol vol = volRepository.findById(formDto.getVol())
                .orElseThrow(() -> new RuntimeException("Vol non trouvé avec l'id: " + formDto.getVol()));

        LocalDateTime dateHeureDepart = vol.getDateHeureDepart();
        Integer mois = dateHeureDepart.getMonthValue();
        Integer annee = dateHeureDepart.getYear();
        Integer idSociete = formDto.getSociete();

        // Créer la diffusion avec mois et année dénormalisés
        DiffusionPublicitaire diffusion = new DiffusionPublicitaire();
        diffusion.setIdSociete(idSociete);
        diffusion.setIdVol(formDto.getVol());
        diffusion.setNombreDiffusion(formDto.getNombreDiffusion());
        diffusion.setMois(mois);
        diffusion.setAnnee(annee);

        // Sauvegarder la diffusion
        DiffusionPublicitaire savedDiffusion = diffusionRepository.save(diffusion);

        // Calculer le montant de cette diffusion
        BigDecimal coutUnitaire = getCoutUnitaire();
        BigDecimal montantDiffusion = coutUnitaire.multiply(BigDecimal.valueOf(formDto.getNombreDiffusion()));

        // Vérifier si une facture_mere existe pour cette société/mois/année
        FactureMere factureMere = factureMereRepository.findByIdSocieteAndMoisAndAnnee(idSociete, mois, annee);

        if (factureMere == null) {
            // Créer une nouvelle facture_mere
            factureMere = new FactureMere();
            factureMere.setIdSociete(idSociete);
            factureMere.setMois(mois);
            factureMere.setAnnee(annee);
            factureMere.setMontantTotal(montantDiffusion);
            factureMere.setMontantPaye(BigDecimal.ZERO);
            factureMere = factureMereRepository.save(factureMere);
        } else {
            // Mettre à jour le montant_total de la facture_mere existante
            BigDecimal nouveauTotal = factureMere.getMontantTotal() != null 
                    ? factureMere.getMontantTotal().add(montantDiffusion) 
                    : montantDiffusion;
            factureMere.setMontantTotal(nouveauTotal);
            factureMere = factureMereRepository.save(factureMere);
        }

        // Créer la facture_diffusion (facture fille)
        FactureDiffusion factureDiffusion = new FactureDiffusion();
        factureDiffusion.setIdFactureMere(factureMere.getIdFactureMere());
        factureDiffusion.setIdDiffusionPublicitaire(savedDiffusion.getIdDiffusionPublicitaire());
        factureDiffusion.setMontant(montantDiffusion);
        factureDiffusionRepository.save(factureDiffusion);

        return savedDiffusion;
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

            // Récupérer facture mère et facture diffusion
            FactureMere factureMere = factureMereRepository
                    .findByIdSocieteAndMoisAndAnnee(idSociete, diffusion.getMois(), diffusion.getAnnee());
            FactureDiffusion factureDiffusion = factureDiffusionRepository
                    .findByIdDiffusionPublicitaire(diffusion.getIdDiffusionPublicitaire());

            // Montant de la diffusion (priorité à la facture de diffusion si disponible)
            BigDecimal montantDiffusion = (factureDiffusion != null && factureDiffusion.getMontant() != null)
                    ? factureDiffusion.getMontant()
                    : coutUnitaire.multiply(BigDecimal.valueOf(diffusion.getNombreDiffusion() != null ? diffusion.getNombreDiffusion() : 0));

            dto.setTotalCout(montantDiffusion);

            // Paiement proportionnel en fonction de la facture mère
            BigDecimal payeMere = (factureMere != null && factureMere.getMontantPaye() != null)
                    ? factureMere.getMontantPaye()
                    : BigDecimal.ZERO;
            BigDecimal totalMere = (factureMere != null && factureMere.getMontantTotal() != null)
                    ? factureMere.getMontantTotal()
                    : BigDecimal.ZERO;

            BigDecimal montantPayeDiffusion = BigDecimal.ZERO;
            if (totalMere != null && totalMere.compareTo(BigDecimal.ZERO) > 0) {
                // ratio = (montant diffusion) / (total facture mère)
                java.math.RoundingMode rm = java.math.RoundingMode.HALF_UP;
                BigDecimal ratio = montantDiffusion.divide(totalMere, 6, rm);
                montantPayeDiffusion = payeMere.multiply(ratio);
                // Ne pas dépasser le montant de la diffusion
                if (montantPayeDiffusion.compareTo(montantDiffusion) > 0) {
                    montantPayeDiffusion = montantDiffusion;
                }
            }

            dto.setMontantPaye(montantPayeDiffusion);

            BigDecimal resteAPayer = montantDiffusion.subtract(montantPayeDiffusion);
            if (resteAPayer.compareTo(BigDecimal.ZERO) < 0) {
                resteAPayer = BigDecimal.ZERO;
            }
            dto.setResteAPayer(resteAPayer);

            result.add(dto);
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
