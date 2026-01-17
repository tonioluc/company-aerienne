package itu.company.aerienne.service;

import itu.company.aerienne.dto.AchatPlaceDto;
import itu.company.aerienne.dto.ChiffreAffaireDto;
import itu.company.aerienne.model.AchatPlaces;
import itu.company.aerienne.model.Aeroport;
import itu.company.aerienne.model.CategorieClient;
import itu.company.aerienne.model.ClassePlace;
import itu.company.aerienne.model.Trajet;
import itu.company.aerienne.model.Vol;
import itu.company.aerienne.repository.AchatPlacesRepository;
import itu.company.aerienne.repository.AeroportRepository;
import itu.company.aerienne.repository.CategorieClientRepository;
import itu.company.aerienne.repository.ClassPlaceRepository;
import itu.company.aerienne.repository.TrajetRepository;
import itu.company.aerienne.repository.VolRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AchatPlacesService {

    @Autowired
    private AchatPlacesRepository repository;

    @Autowired
    private ClassPlaceRepository classPlaceRepository;

    @Autowired
    private CategorieClientRepository categorieClientRepository;

    @Autowired
    private VolRepository volRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private AeroportRepository aeroportRepository;

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

    public List<AchatPlaceDto> findAllDto() {
        List<AchatPlaces> achats = repository.findAll();
        List<AchatPlaceDto> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (AchatPlaces achat : achats) {
            AchatPlaceDto dto = new AchatPlaceDto();
            dto.setIdAchatPlaces(achat.getIdAchatPlaces());
            dto.setNomClient(achat.getNomClient());

            Integer nbPlaces = null;
            try {
                nbPlaces = Integer.valueOf(achat.getNombrePlace());
            } catch (NumberFormatException e) {
                nbPlaces = null;
            }
            dto.setNombrePlace(nbPlaces);

            // Classe
            String classeLibelle = "";
            if (achat.getIdClassePlace() != null) {
                Optional<ClassePlace> classeOpt = classPlaceRepository.findById(achat.getIdClassePlace());
                classeLibelle = classeOpt.map(ClassePlace::getLibelle).orElse("");
            }
            dto.setClasseLibelle(classeLibelle);

            // Catégorie
            String categorieLibelle = "Adulte";
            if (achat.getIdCategorieClient() != null) {
                Optional<CategorieClient> catOpt = categorieClientRepository.findById(achat.getIdCategorieClient());
                categorieLibelle = catOpt.map(CategorieClient::getLibelle).orElse("");
            }
            dto.setCategorieLibelle(categorieLibelle);

            // Vol
            String volDescription = "";
            if (achat.getIdVol() != null) {
                Optional<Vol> volOpt = volRepository.findById(achat.getIdVol());
                if (volOpt.isPresent()) {
                    Vol vol = volOpt.get();

                    String departLabel = "";
                    String arriveLabel = "";

                    if (vol.getIdTrajet() != null) {
                        Optional<Trajet> trajetOpt = trajetRepository.findById(vol.getIdTrajet());
                        if (trajetOpt.isPresent()) {
                            Trajet trajet = trajetOpt.get();
                            departLabel = formatAeroport(trajet.getIdAeroportDepart());
                            arriveLabel = formatAeroport(trajet.getIdAeroportArrive());
                        }
                    }

                    String departDate = vol.getDateHeureDepart() != null ? vol.getDateHeureDepart().format(formatter)
                            : "";
                    String arriveDate = vol.getDateHeureArrive() != null ? vol.getDateHeureArrive().format(formatter)
                            : "";

                    volDescription = String.format("%s -> %s | %s -> %s", departLabel, arriveLabel, departDate,
                            arriveDate);
                }
            }
            dto.setVolDescription(volDescription);

            result.add(dto);
        }

        return result;
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

    private String formatAeroport(Integer idAeroport) {
        if (idAeroport == null) {
            return "";
        }
        Optional<Aeroport> aeroportOpt = aeroportRepository.findById(idAeroport);
        if (aeroportOpt.isPresent()) {
            Aeroport aeroport = aeroportOpt.get();
            String code = aeroport.getCodeIATA() != null ? aeroport.getCodeIATA() : "";
            String ville = aeroport.getVille() != null ? aeroport.getVille() : "";
            if (!code.isEmpty() && !ville.isEmpty()) {
                return code + " - " + ville;
            }
            return !code.isEmpty() ? code : ville;
        }
        return "";
    }
}
