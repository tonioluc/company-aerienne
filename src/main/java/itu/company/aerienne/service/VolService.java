package itu.company.aerienne.service;

import itu.company.aerienne.dto.AchatDetailDto;
import itu.company.aerienne.dto.VolChiffreAffaireDto;
import itu.company.aerienne.dto.VolDisponibiliteDto;
import itu.company.aerienne.dto.ClasseDisponibiliteDto;
import itu.company.aerienne.model.AchatPlaces;
import itu.company.aerienne.model.CategorieClient;
import itu.company.aerienne.model.ClassePlace;
import itu.company.aerienne.model.PrixParCategorie;
import itu.company.aerienne.model.PrixVol;
import itu.company.aerienne.model.Vol;
import itu.company.aerienne.model.Avion;
import itu.company.aerienne.model.Trajet;
import itu.company.aerienne.model.Aeroport;
import itu.company.aerienne.repository.TrajetRepository;
import itu.company.aerienne.repository.AeroportRepository;
import itu.company.aerienne.repository.VolRepository;
import itu.company.aerienne.repository.AvionRepository;
import itu.company.aerienne.repository.AchatPlacesRepository;
import itu.company.aerienne.repository.CategorieClientRepository;
import itu.company.aerienne.repository.PrixParCategorieRepository;
import itu.company.aerienne.repository.PrixVolRepository;
import itu.company.aerienne.repository.ClassPlaceRepository;

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

    // @Autowired
    // private VolAvionRepository volAvionRepository;

    @Autowired
    private AchatPlacesRepository achatPlacesRepository;

    @Autowired
    private PrixVolRepository prixVolRepository;

    @Autowired
    private ClassPlaceRepository classePlaceRepository;

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private PrixParCategorieRepository prixParCategorieRepository;

    @Autowired
    private CategorieClientRepository categorieClientRepository;
    
    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private AeroportRepository aeroportRepository;

    public List<Vol> findAll() {
        return repository.findAll();
    }

    /**
     * Retourne la liste des vols avec leurs détails : avion, classes et prix par catégorie.
     */
    public java.util.List<itu.company.aerienne.dto.VolDetailsDto> getAllVolDetails() {
        java.util.List<itu.company.aerienne.dto.VolDetailsDto> result = new java.util.ArrayList<>();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        java.util.List<Vol> vols = repository.findAll();
        for (Vol vol : vols) {
            itu.company.aerienne.dto.VolDetailsDto dto = new itu.company.aerienne.dto.VolDetailsDto();
            dto.setIdVol(vol.getIdVol());
            dto.setDateHeureDepart(vol.getDateHeureDepart() != null ? vol.getDateHeureDepart().format(formatter) : "");
            dto.setDateHeureArrive(vol.getDateHeureArrive() != null ? vol.getDateHeureArrive().format(formatter) : "");

            // Trajet description
            String trajetDesc = "";
            if (vol.getIdTrajet() != null) {
                Optional<Trajet> trajetOpt = trajetRepository.findById(vol.getIdTrajet());
                if (trajetOpt.isPresent()) {
                    Trajet t = trajetOpt.get();
                    String dep = formatAeroport(t.getIdAeroportDepart());
                    String arr = formatAeroport(t.getIdAeroportArrive());
                    trajetDesc = dep + " -> " + arr;
                }
            }
            dto.setTrajetDescription(trajetDesc);

            // Avion
            if (vol.getIdAvion() != null) {
                Optional<Avion> aOpt = avionRepository.findById(vol.getIdAvion());
                if (aOpt.isPresent()) {
                    dto.setAvionModele(aOpt.get().getModele());
                    dto.setAvionCapacite(aOpt.get().getCapacite());
                }
            }

            // Classes/prix
            java.util.List<PrixVol> prixVols = prixVolRepository.findByIdVol(vol.getIdVol());
            for (PrixVol pv : prixVols) {
                itu.company.aerienne.dto.ClassePrixDto cp = new itu.company.aerienne.dto.ClassePrixDto();
                cp.setIdClassePlace(pv.getIdClassePlace());
                if (pv.getPrix() != null) {
                    cp.setPrixUnitaire(java.math.BigDecimal.valueOf(pv.getPrix()));
                } else {
                    cp.setPrixUnitaire(java.math.BigDecimal.ZERO);
                }
                cp.setNbrPlaces(pv.getNbrPlaces());

                // libelle classe
                Optional<ClassePlace> classeOpt = classePlaceRepository.findById(pv.getIdClassePlace());
                classeOpt.ifPresent(c -> cp.setLibelle(c.getLibelle()));

                // pour chaque catégorie, vérifier s'il y a un prix spécial
                java.util.List<CategorieClient> categories = categorieClientRepository.findAll();
                for (CategorieClient cat : categories) {
                    itu.company.aerienne.dto.CategoriePrixDto catDto = new itu.company.aerienne.dto.CategoriePrixDto();
                    catDto.setIdCategorie(cat.getIdCategorieClient());
                    catDto.setLibelle(cat.getLibelle());

                    Optional<PrixParCategorie> ppcOpt = prixParCategorieRepository.findByIdCategoriePersonneAndIdClassePlace(
                            cat.getIdCategorieClient(), pv.getIdClassePlace());
                    if (ppcOpt.isPresent()) {
                        PrixParCategorie ppc = ppcOpt.get();
                        catDto.setPrix(ppc.getPrix());
                        catDto.setPourcentage(ppc.getPourcentage());
                    }

                    cp.addCategoriePrix(catDto);
                }

                dto.addClasse(cp);
            }

            result.add(dto);
        }

        return result;
    }

    private String formatAeroport(Integer idAeroport) {
        if (idAeroport == null) return "";
        Optional<Aeroport> opt = aeroportRepository.findById(idAeroport);
        if (!opt.isPresent()) return "";
        Aeroport a = opt.get();
        String code = a.getCodeIATA() != null ? a.getCodeIATA() : "";
        String ville = a.getVille() != null ? a.getVille() : "";
        if (!code.isEmpty() && !ville.isEmpty()) return code + " - " + ville;
        return !code.isEmpty() ? code : ville;
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
     * 
     * Logique de calcul du prix :
     * 1. Si l'achat a un idCategorieClient, on cherche un prix spécial dans
     * prix_par_categorie
     * 2. Si pas de prix spécial pour cette catégorie/classe, on prend le prix de
     * prix_vol
     * 3. Si idCategorieClient est null, on prend directement le prix de prix_vol
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

            // Récupérer le modèle de l'avion pour description
            if (vol.getIdAvion() != null) {
                Optional<Avion> avionOpt = avionRepository.findById(vol.getIdAvion());
                if (avionOpt.isPresent()) {
                    dto.setAvionModele(avionOpt.get().getModele());
                    dto.setAvionCapacite(avionOpt.get().getCapacite());
                }
            }

            // Récupérer tous les prix pour ce vol (pour lookup rapide)
            List<PrixVol> prixVolList = prixVolRepository.findByIdVol(vol.getIdVol());
            Map<Integer, PrixVol> prixParClasse = new HashMap<>();
            for (PrixVol pv : prixVolList) {
                prixParClasse.put(pv.getIdClassePlace(), pv);
            }

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

                BigDecimal prixUnitaire = BigDecimal.ZERO;
                String classeLibelle = "";
                String categorieLibelle = "Adulte";
                Integer idClassePlace = achat.getIdClassePlace();
                Integer idCategorieClient = achat.getIdCategorieClient();

                // Récupérer le libellé de la classe
                if (idClassePlace != null) {
                    Optional<ClassePlace> classeOpt = classePlaceRepository.findById(idClassePlace);
                    if (classeOpt.isPresent()) {
                        classeLibelle = classeOpt.get().getLibelle();
                    }
                }

                // Récupérer le libellé de la catégorie si elle existe
                if (idCategorieClient != null) {
                    Optional<CategorieClient> categorieOpt = categorieClientRepository.findById(idCategorieClient);
                    if (categorieOpt.isPresent()) {
                        categorieLibelle = categorieOpt.get().getLibelle();
                    }
                }

                // Logique de calcul du prix
                // D'abord récupérer le prix de base depuis prix_vol pour cette classe
                BigDecimal prixBase = BigDecimal.ZERO;
                if (idClassePlace != null && prixParClasse.containsKey(idClassePlace)) {
                    PrixVol prixVol = prixParClasse.get(idClassePlace);
                    prixBase = prixVol.getPrix() != null ? BigDecimal.valueOf(prixVol.getPrix()) : BigDecimal.ZERO;
                }

                if (idCategorieClient != null && idClassePlace != null) {
                    // Chercher un prix ou pourcentage pour cette catégorie et classe
                    Optional<PrixParCategorie> prixSpecialOpt = prixParCategorieRepository
                            .findByIdCategoriePersonneAndIdClassePlace(idCategorieClient, idClassePlace);

                    if (prixSpecialOpt.isPresent()) {
                        PrixParCategorie ppc = prixSpecialOpt.get();
                        if (ppc.getPrix() != null) {
                            // Prix fixe défini, l'utiliser directement
                            prixUnitaire = BigDecimal.valueOf(ppc.getPrix());
                        } else if (ppc.getPourcentage() != null) {
                            // Pas de prix fixe, calculer avec le pourcentage
                            // prixUnitaire = prixBase * pourcentage / 100
                            Double pourcentage = ppc.getPourcentage();
                            prixUnitaire = prixBase.multiply(BigDecimal.valueOf(pourcentage))
                                    .divide(BigDecimal.valueOf(100));
                        } else {
                            // Ni prix ni pourcentage, utiliser le prix de base
                            prixUnitaire = prixBase;
                        }
                    } else {
                        // Pas de configuration spéciale, utiliser le prix de base
                        prixUnitaire = prixBase;
                    }
                } else {
                    // Pas de catégorie client, utiliser directement le prix de base
                    prixUnitaire = prixBase;
                }

                BigDecimal prixTotal = prixUnitaire.multiply(BigDecimal.valueOf(nbPlaces));

                AchatDetailDto achatDetail = new AchatDetailDto();
                achatDetail.setIdAchat(achat.getIdAchatPlaces());
                achatDetail.setNomClient(achat.getNomClient());
                achatDetail.setNombrePlaces(nbPlaces);
                achatDetail.setPrixUnitaire(prixUnitaire);
                achatDetail.setPrixTotal(prixTotal);
                achatDetail.setClasseLibelle(classeLibelle);
                achatDetail.setCategorieLibelle(categorieLibelle);

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
     * @param idAeroportDepart ID de l'aéroport de départ
     * @param idAeroportArrive ID de l'aéroport d'arrivée
     * @param nombrePlaceEntre Nombre de places demandées par le client
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
     * Retourne la disponibilité par classe pour les vols d'un trajet.
     * Filtre les vols si la classe choisie n'existe pas ou n'a pas assez de places.
     */
    public List<VolDisponibiliteDto> getVolDisponibleParClasse(int idAeroportDepart, int idAeroportArrive,
            int nombrePlaceEntre, Integer idClassePlaceChoisie) {
        List<VolDisponibiliteDto> result = new ArrayList<>();
        List<Vol> vols = repository.findByAeroports(idAeroportDepart, idAeroportArrive);

        for (Vol vol : vols) {
            // Récupérer prix/places par classe pour ce vol
            List<PrixVol> prixVols = prixVolRepository.findByIdVol(vol.getIdVol());
            List<ClasseDisponibiliteDto> classes = new ArrayList<>();

            boolean classeChoisieExiste = false;
            boolean enoughInChosenClass = false;

            for (PrixVol pv : prixVols) {
                // total réservé pour cette classe
                Integer reserved = achatPlacesRepository.getTotalPlacesAcheteesByVolAndClasse(vol.getIdVol(),
                        pv.getIdClassePlace());
                if (reserved == null)
                    reserved = 0;
                int available = (pv.getNbrPlaces() != null ? pv.getNbrPlaces() : 0) - reserved;

                // Libellé de la classe
                String libelle = "";
                Optional<ClassePlace> classeOpt = classePlaceRepository.findById(pv.getIdClassePlace());
                if (classeOpt.isPresent())
                    libelle = classeOpt.get().getLibelle();

                classes.add(new ClasseDisponibiliteDto(pv.getIdClassePlace(), libelle, available));

                // Vérifier si c'est la classe choisie
                if (idClassePlaceChoisie != null && pv.getIdClassePlace().equals(idClassePlaceChoisie)) {
                    classeChoisieExiste = true;
                    enoughInChosenClass = available >= nombrePlaceEntre;
                }
            }

            // Si aucune classe choisie, afficher tous les vols
            // Sinon, ne garder que les vols où la classe existe ET a assez de places
            if (idClassePlaceChoisie == null || (classeChoisieExiste && enoughInChosenClass)) {
                result.add(new VolDisponibiliteDto(vol, classes));
            }
        }

        return result;
    }

    /**
     * Calcule la capacité totale d'un vol basée sur les avions affectés.
     */
    private int getCapaciteTotaleVol(Integer idVol) {
        // Default capacity if anything missing
        final int DEFAULT_CAPACITY = 150;

        Optional<Vol> volOpt = repository.findById(idVol);
        if (!volOpt.isPresent())
            return DEFAULT_CAPACITY;
        Vol vol = volOpt.get();
        Integer idAvion = vol.getIdAvion();
        if (idAvion == null)
            return DEFAULT_CAPACITY;

        Optional<Avion> avionOpt = avionRepository.findById(idAvion);
        if (!avionOpt.isPresent())
            return DEFAULT_CAPACITY;

        Integer capacite = avionOpt.get().getCapacite();
        return capacite != null ? capacite : DEFAULT_CAPACITY;
    }
}
