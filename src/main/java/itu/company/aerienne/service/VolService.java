package itu.company.aerienne.service;

import itu.company.aerienne.dto.AchatDetailDto;
import itu.company.aerienne.dto.VolChiffreAffaireDto;
import itu.company.aerienne.model.AchatPlaces;
import itu.company.aerienne.model.Vol;
import itu.company.aerienne.model.VolAvion;
import itu.company.aerienne.repository.VolRepository;
import itu.company.aerienne.repository.VolAvionRepository;
import itu.company.aerienne.repository.AchatPlacesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VolService {

    @Autowired
    private VolRepository repository;

    @Autowired
    private VolAvionRepository volAvionRepository;

    @Autowired
    private AchatPlacesRepository achatPlacesRepository;

    public List<Vol> findAll() {
        return repository.findAll();
    }

    public Optional<Vol> findById(Integer id) {
        return repository.findById(id);
    }

    public Vol save(Vol Vol) {
        return repository.save(Vol);
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    /**
     * Retourne les vols d'une date donnée avec leurs détails de chiffre d'affaires.
     */
    public List<VolChiffreAffaireDto> getChiffreAffaireDetailByDate(LocalDate date) {
        List<VolChiffreAffaireDto> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        List<Vol> vols = repository.findByDate(date);

        for (Vol vol : vols) {
            VolChiffreAffaireDto dto = new VolChiffreAffaireDto();
            dto.setIdVol(vol.getIdVol());
            dto.setDateHeureDepart(vol.getDateHeureDepart() != null ? vol.getDateHeureDepart().format(formatter) : "");
            dto.setDateHeureArrive(vol.getDateHeureArrive() != null ? vol.getDateHeureArrive().format(formatter) : "");
            dto.setPrixPlace(vol.getPrixPlace() != null ? vol.getPrixPlace() : BigDecimal.ZERO);

            // Récupérer les achats pour ce vol
            List<AchatPlaces> achats = achatPlacesRepository.findByIdVol(vol.getIdVol());
            
            int totalPlaces = 0;
            BigDecimal totalCA = BigDecimal.ZERO;

            for (AchatPlaces achat : achats) {
                int nbPlaces = 0;
                try {
                    nbPlaces = Integer.parseInt(achat.getNombrePlace());
                } catch (NumberFormatException e) {
                    nbPlaces = 0;
                }

                BigDecimal prixUnitaire = vol.getPrixPlace() != null ? vol.getPrixPlace() : BigDecimal.ZERO;
                BigDecimal prixTotal = prixUnitaire.multiply(BigDecimal.valueOf(nbPlaces));

                AchatDetailDto achatDetail = new AchatDetailDto();
                achatDetail.setIdAchat(achat.getIdAchatPlaces());
                achatDetail.setNomClient(achat.getNomClient());
                achatDetail.setNombrePlaces(nbPlaces);
                achatDetail.setPrixUnitaire(prixUnitaire);
                achatDetail.setPrixTotal(prixTotal);

                dto.addAchat(achatDetail);

                totalPlaces += nbPlaces;
                totalCA = totalCA.add(prixTotal);
            }

            dto.setTotalPlacesVendues(totalPlaces);
            dto.setChiffreAffaires(totalCA);

            result.add(dto);
        }

        return result;
    }

    /**
     * Retourne les vols disponibles avec le nombre de places restantes.
     * Filtre les vols qui n'ont pas assez de places par rapport à nombrePlaceEntre.
     *
     * @param idAeroportDepart  ID de l'aéroport de départ
     * @param idAeroportArrive  ID de l'aéroport d'arrivée
     * @param nombrePlaceEntre  Nombre de places demandées par le client
     * @return Map<Vol, Integer> où Integer représente les places disponibles
     */
    public Map<Vol, Integer> getVolDisponible(int idAeroportDepart, int idAeroportArrive, int nombrePlaceEntre) {
        Map<Vol, Integer> volsDisponibles = new HashMap<>();

        // Récupérer les vols correspondant aux aéroports
        List<Vol> vols = repository.findByAeroports(idAeroportDepart, idAeroportArrive);

        for (Vol vol : vols) {
            // Calculer la capacité totale des avions affectés à ce vol
            int capaciteTotale = getCapaciteTotaleVol(vol.getIdVol());

            // Récupérer le nombre de places déjà achetées
            Integer placesAchetees = achatPlacesRepository.getTotalPlacesAcheteesByVol(vol.getIdVol());
            if (placesAchetees == null) {
                placesAchetees = 0;
            }

            // Calculer les places disponibles
            int placesDisponibles = capaciteTotale - placesAchetees;

            // Ajouter le vol seulement s'il a assez de places
            if (placesDisponibles >= nombrePlaceEntre) {
                volsDisponibles.put(vol, placesDisponibles);
            }
        }

        return volsDisponibles;
    }

    /**
     * Calcule la capacité totale d'un vol basée sur les avions affectés.
     */
    private int getCapaciteTotaleVol(Integer idVol) {
        List<VolAvion> volAvions = volAvionRepository.findByIdVol(idVol);
        // Pour simplifier, on suppose une capacité par défaut si pas d'avion affecté
        // Dans un vrai système, on récupérerait la capacité de chaque avion
        return volAvions.size() > 0 ? volAvions.size() * 150 : 150; // 150 places par défaut
    }
}
