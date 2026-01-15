package itu.company.aerienne.service;

import itu.company.aerienne.dto.ClassePrixDto;
import itu.company.aerienne.dto.ValeurMaxParVolParAvionDto;
import itu.company.aerienne.dto.ValeurMaxVolDto;
import itu.company.aerienne.model.Aeroport;
import itu.company.aerienne.model.Avion;
import itu.company.aerienne.model.ClassePlace;
import itu.company.aerienne.model.PrixVol;
import itu.company.aerienne.model.Trajet;
import itu.company.aerienne.model.Vol;
import itu.company.aerienne.repository.AeroportRepository;
import itu.company.aerienne.repository.AvionRepository;
import itu.company.aerienne.repository.ClassPlaceRepository;
import itu.company.aerienne.repository.PrixVolRepository;
import itu.company.aerienne.repository.TrajetRepository;
import itu.company.aerienne.repository.VolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ValeurMaximaleService {

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private VolRepository volRepository;

    @Autowired
    private PrixVolRepository prixVolRepository;

    @Autowired
    private ClassPlaceRepository classePlaceRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private AeroportRepository aeroportRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Calcule la valeur maximale que peut générer un avion pour tous ses vols.
     */
    public ValeurMaxParVolParAvionDto getValeurMaximaleParAvion(Integer idAvion) {
        Optional<Avion> avionOpt = avionRepository.findById(idAvion);
        if (!avionOpt.isPresent()) {
            return null;
        }

        Avion avion = avionOpt.get();
        BigDecimal capacite = avion.getCapacite() != null ? BigDecimal.valueOf(avion.getCapacite()) : BigDecimal.ZERO;
        
        ValeurMaxParVolParAvionDto result = new ValeurMaxParVolParAvionDto(
            avion.getId_avion(),
            avion.getModele(),
            capacite
        );

        // Récupérer tous les vols de cet avion
        List<Vol> vols = volRepository.findByIdAvion(idAvion);

        for (Vol vol : vols) {
            ValeurMaxVolDto volDto = buildValeurMaxVolDto(vol);
            result.addVol(volDto);
        }

        return result;
    }

    /**
     * Construit le DTO ValeurMaxVolDto pour un vol donné.
     */
    private ValeurMaxVolDto buildValeurMaxVolDto(Vol vol) {
        String depart = buildLieuDateHeure(vol.getIdTrajet(), true, vol);
        String arrive = buildLieuDateHeure(vol.getIdTrajet(), false, vol);

        ValeurMaxVolDto volDto = new ValeurMaxVolDto(vol.getIdVol(), depart, arrive);

        // Récupérer les prix par classe pour ce vol
        List<PrixVol> prixVols = prixVolRepository.findByIdVol(vol.getIdVol());

        for (PrixVol prixVol : prixVols) {
            ClassePrixDto classePrix = buildClassePrixDto(prixVol);
            volDto.addClassePrix(classePrix);
        }

        return volDto;
    }

    /**
     * Construit une chaîne "Ville (CODE_IATA) - dd/MM/yyyy HH:mm" pour départ ou arrivée.
     */
    private String buildLieuDateHeure(Integer idTrajet, boolean isDepart, Vol vol) {
        Optional<Trajet> trajetOpt = trajetRepository.findById(idTrajet);
        if (!trajetOpt.isPresent()) {
            return "Inconnu";
        }

        Trajet trajet = trajetOpt.get();
        Integer idAeroport = isDepart ? trajet.getIdAeroportDepart() : trajet.getIdAeroportArrive();
        
        Optional<Aeroport> aeroportOpt = aeroportRepository.findById(idAeroport);
        String lieuStr = "Inconnu";
        if (aeroportOpt.isPresent()) {
            Aeroport aeroport = aeroportOpt.get();
            lieuStr = aeroport.getVille() + " (" + aeroport.getCodeIATA() + ")";
        }

        String dateHeure = "";
        if (isDepart && vol.getDateHeureDepart() != null) {
            dateHeure = vol.getDateHeureDepart().format(DATE_FORMATTER);
        } else if (!isDepart && vol.getDateHeureArrive() != null) {
            dateHeure = vol.getDateHeureArrive().format(DATE_FORMATTER);
        }

        return lieuStr + " - " + dateHeure;
    }

    /**
     * Construit le DTO ClassePrixDto à partir d'un PrixVol.
     */
    private ClassePrixDto buildClassePrixDto(PrixVol prixVol) {
        String libelle = "Classe " + prixVol.getIdClassePlace();
        
        // Récupérer le libellé de la classe
        Optional<ClassePlace> classeOpt = classePlaceRepository.findById(prixVol.getIdClassePlace());
        if (classeOpt.isPresent()) {
            libelle = classeOpt.get().getLibelle();
        }

        BigDecimal prix = prixVol.getPrix() != null ? BigDecimal.valueOf(prixVol.getPrix()) : BigDecimal.ZERO;
        Integer nbrPlaces = prixVol.getNbrPlaces() != null ? prixVol.getNbrPlaces() : 0;

        return new ClassePrixDto(
            prixVol.getIdClassePlace(),
            libelle,
            prix,
            nbrPlaces
        );
    }
}
