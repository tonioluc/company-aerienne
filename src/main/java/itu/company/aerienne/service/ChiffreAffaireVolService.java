package itu.company.aerienne.service;

import itu.company.aerienne.dto.ChiffreAffaireVolDto;
import itu.company.aerienne.dto.ProduitVenduDetailDto;
import itu.company.aerienne.dto.VolInfoDto;
import itu.company.aerienne.model.*;
import itu.company.aerienne.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChiffreAffaireVolService {

    @Autowired
    private VolRepository volRepository;

    @Autowired
    private AchatPlacesRepository achatPlacesRepository;

    @Autowired
    private PrixVolRepository prixVolRepository;

    @Autowired
    private PrixParCategorieRepository prixParCategorieRepository;

    @Autowired
    private DiffusionPublicitaireRepository diffusionRepository;

    @Autowired
    private CoutDiffusionRepository coutDiffusionRepository;

    @Autowired
    private FactureMereRepository factureMereRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private AeroportRepository aeroportRepository;

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private VenteProduitExtraRepository venteProduitExtraRepository;

    @Autowired
    private ProduitExtraRepository produitExtraRepository;

    /**
     * Récupère le chiffre d'affaires pour tous les vols
     */
    public List<ChiffreAffaireVolDto> getChiffreAffaireVols() {
        List<ChiffreAffaireVolDto> result = new ArrayList<>();
        List<Vol> vols = volRepository.findAll();

        for (Vol vol : vols) {
            ChiffreAffaireVolDto dto = new ChiffreAffaireVolDto();
            
            // Infos du vol
            VolInfoDto volInfo = buildVolInfo(vol);
            dto.setVol(volInfo);

            // Montant des tickets vendus (depuis achat_places)
            BigDecimal montantTickets = calculateMontantTicketsVendus(vol.getIdVol());
            dto.setMontantTicketVendu(montantTickets);

            // Montant de la publicité (depuis diffusion_publicitaire)
            BigDecimal montantPub = calculateMontantPublicite(vol.getIdVol());
            dto.setMontantPublicite(montantPub);

            // Montant des produits extra vendus
            BigDecimal montantProduitExtra = calculateMontantProduitExtra(vol.getIdVol());
            dto.setMontantProduitExtra(montantProduitExtra);

            // Détails des produits vendus pour ce vol
            List<ProduitVenduDetailDto> produitsVendus = getProduitsVendusDetails(vol.getIdVol());
            dto.setProduitsVendus(produitsVendus);

            // Montant payé (depuis facture_mere via diffusion)
            BigDecimal montantPaye = calculateMontantPaye(vol.getIdVol());
            dto.setMontantAPayer(montantPaye);

            // Calculer les totaux
            dto.calculateTotals();

            result.add(dto);
        }

        return result;
    }

    /**
     * Construit les infos du vol (aéroports, avion, dates)
     */
    private VolInfoDto buildVolInfo(Vol vol) {
        VolInfoDto info = new VolInfoDto();
        info.setIdVol(vol.getIdVol());
        info.setDateHeureDepart(vol.getDateHeureDepart());
        info.setDateHeureArrivee(vol.getDateHeureArrive());

        // Récupérer le trajet
        Trajet trajet = trajetRepository.findById(vol.getIdTrajet()).orElse(null);
        if (trajet != null) {
            Aeroport depart = aeroportRepository.findById(trajet.getIdAeroportDepart()).orElse(null);
            Aeroport arrivee = aeroportRepository.findById(trajet.getIdAeroportArrive()).orElse(null);
            info.setAeroportDepart(depart != null ? depart.getVille() + " (" + depart.getCodeIATA() + ")" : "N/A");
            info.setAeroportArrivee(arrivee != null ? arrivee.getVille() + " (" + arrivee.getCodeIATA() + ")" : "N/A");
        }

        // Récupérer l'avion
        Avion avion = avionRepository.findById(vol.getIdAvion()).orElse(null);
        info.setAvion(avion != null ? avion.getModele() : "N/A");

        return info;
    }

    /**
     * Calcule le montant total des tickets vendus pour un vol
     * En prenant en compte le prix par catégorie client et classe de place
     */
    private BigDecimal calculateMontantTicketsVendus(Integer idVol) {
        BigDecimal total = BigDecimal.ZERO;
        List<AchatPlaces> achats = achatPlacesRepository.findByIdVol(idVol);

        for (AchatPlaces achat : achats) {
            int nombrePlaces = 0;
            try {
                nombrePlaces = Integer.parseInt(achat.getNombrePlace());
            } catch (NumberFormatException e) {
                continue;
            }

            BigDecimal prixUnitaire = BigDecimal.ZERO;

            // 1. Chercher d'abord le prix par catégorie client
            if (achat.getIdCategorieClient() != null && achat.getIdClassePlace() != null) {
                var prixCategorie = prixParCategorieRepository.findByIdVolAndIdCategoriePersonneAndIdClassePlace(
                        idVol, achat.getIdCategorieClient(), achat.getIdClassePlace());
                
                if (prixCategorie.isPresent()) {
                    PrixParCategorie pc = prixCategorie.get();
                    if (pc.getPrix() != null) {
                        prixUnitaire = BigDecimal.valueOf(pc.getPrix());
                    } else if (pc.getPourcentage() != null) {
                        // Calculer le prix basé sur le pourcentage du prix_vol
                        var prixVol = prixVolRepository.findByIdVolAndIdClassePlace(idVol, achat.getIdClassePlace());
                        if (prixVol.isPresent() && prixVol.get().getPrix() != null) {
                            prixUnitaire = BigDecimal.valueOf(prixVol.get().getPrix())
                                    .multiply(BigDecimal.valueOf(pc.getPourcentage()))
                                    .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
                        }
                    }
                }
            }

            // 2. Si pas de prix catégorie, utiliser le prix_vol standard
            if (prixUnitaire.compareTo(BigDecimal.ZERO) == 0 && achat.getIdClassePlace() != null) {
                var prixVol = prixVolRepository.findByIdVolAndIdClassePlace(idVol, achat.getIdClassePlace());
                if (prixVol.isPresent() && prixVol.get().getPrix() != null) {
                    prixUnitaire = BigDecimal.valueOf(prixVol.get().getPrix());
                }
            }

            total = total.add(prixUnitaire.multiply(BigDecimal.valueOf(nombrePlaces)));
        }

        return total;
    }

    /**
     * Calcule le montant total de la publicité pour un vol
     */
    private BigDecimal calculateMontantPublicite(Integer idVol) {
        BigDecimal coutUnitaire = getCoutUnitaire();
        Integer totalDiffusions = diffusionRepository.getTotalDiffusionsByVol(idVol);
        return coutUnitaire.multiply(BigDecimal.valueOf(totalDiffusions != null ? totalDiffusions : 0));
    }

    /**
     * Calcule le montant payé pour la publicité d'un vol
     * En se basant sur les factures liées aux diffusions de ce vol
     */
    private BigDecimal calculateMontantPaye(Integer idVol) {
        BigDecimal totalPaye = BigDecimal.ZERO;
        List<DiffusionPublicitaire> diffusions = diffusionRepository.findByIdVol(idVol);
        
        for (DiffusionPublicitaire diffusion : diffusions) {
            // Trouver la facture_mere correspondante (même société, mois, année)
            FactureMere facture = factureMereRepository.findByIdSocieteAndMoisAndAnnee(
                    diffusion.getIdSociete(), diffusion.getMois(), diffusion.getAnnee());
            
            if (facture != null && facture.getMontantPaye() != null) {
                // Calculer la proportion payée pour cette diffusion
                BigDecimal montantTotal = facture.getMontantTotal() != null ? facture.getMontantTotal() : BigDecimal.ONE;
                if (montantTotal.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal coutDiffusion = getCoutUnitaire().multiply(BigDecimal.valueOf(diffusion.getNombreDiffusion()));
                    BigDecimal proportionPaye = facture.getMontantPaye().multiply(coutDiffusion).divide(montantTotal, 2, java.math.RoundingMode.HALF_UP);
                    totalPaye = totalPaye.add(proportionPaye);
                }
            }
        }
        
        return totalPaye;
    }

    /**
     * Récupère le coût unitaire de diffusion
     */
    private BigDecimal getCoutUnitaire() {
        List<CoutDiffusion> couts = coutDiffusionRepository.findAll();
        if (!couts.isEmpty() && couts.get(0).getCoutUnitaire() != null) {
            return couts.get(0).getCoutUnitaire();
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calcule le montant total des produits extra vendus pour un vol
     */
    private BigDecimal calculateMontantProduitExtra(Integer idVol) {
        BigDecimal total = BigDecimal.ZERO;
        List<VenteProduitExtra> ventes = venteProduitExtraRepository.findByIdVol(idVol);

        for (VenteProduitExtra vente : ventes) {
            if (vente.getQuantite() != null && vente.getIdProduitExtra() != null) {
                ProduitExtra produit = produitExtraRepository.findById(vente.getIdProduitExtra()).orElse(null);
                if (produit != null && produit.getPrixUnitaire() != null) {
                    BigDecimal montantVente = produit.getPrixUnitaire().multiply(BigDecimal.valueOf(vente.getQuantite()));
                    total = total.add(montantVente);
                }
            }
        }

        return total;
    }

    /**
     * Récupère les détails des produits vendus pour un vol (agrégés par produit)
     */
    private List<ProduitVenduDetailDto> getProduitsVendusDetails(Integer idVol) {
        List<ProduitVenduDetailDto> result = new ArrayList<>();
        List<VenteProduitExtra> ventes = venteProduitExtraRepository.findByIdVol(idVol);

        // Agréger les quantités par produit
        Map<Integer, Integer> quantiteParProduit = new HashMap<>();
        for (VenteProduitExtra vente : ventes) {
            if (vente.getQuantite() != null && vente.getIdProduitExtra() != null) {
                quantiteParProduit.merge(vente.getIdProduitExtra(), vente.getQuantite(), Integer::sum);
            }
        }

        // Créer les DTOs
        for (Map.Entry<Integer, Integer> entry : quantiteParProduit.entrySet()) {
            ProduitExtra produit = produitExtraRepository.findById(entry.getKey()).orElse(null);
            if (produit != null) {
                result.add(new ProduitVenduDetailDto(
                    produit.getNom(),
                    entry.getValue(),
                    produit.getPrixUnitaire()
                ));
            }
        }

        // Trier par nom de produit
        result.sort((a, b) -> a.getNomProduit().compareToIgnoreCase(b.getNomProduit()));

        return result;
    }
}
