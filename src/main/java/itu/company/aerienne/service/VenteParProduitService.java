package itu.company.aerienne.service;

import itu.company.aerienne.dto.VenteParProduitDto;
import itu.company.aerienne.model.ProduitExtra;
import itu.company.aerienne.model.VenteProduitExtra;
import itu.company.aerienne.repository.ProduitExtraRepository;
import itu.company.aerienne.repository.VenteProduitExtraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VenteParProduitService {

    @Autowired
    private VenteProduitExtraRepository venteProduitExtraRepository;

    @Autowired
    private ProduitExtraRepository produitExtraRepository;

    public List<VenteParProduitDto> getVentesParProduit(Integer mois, Integer annee) {
        List<VenteProduitExtra> allVentes = venteProduitExtraRepository.findAll();
        List<ProduitExtra> allProduits = produitExtraRepository.findAll();

        // Créer un map pour accéder rapidement aux produits
        Map<Integer, ProduitExtra> produitMap = new HashMap<>();
        for (ProduitExtra p : allProduits) {
            produitMap.put(p.getIdProduitExtra(), p);
        }

        // Filtrer par mois et année si spécifiés
        List<VenteProduitExtra> ventesFiltrees = new ArrayList<>();
        for (VenteProduitExtra v : allVentes) {
            if (v.getDateVente() != null) {
                boolean matchMois = (mois == null || v.getDateVente().getMonthValue() == mois);
                boolean matchAnnee = (annee == null || v.getDateVente().getYear() == annee);
                if (matchMois && matchAnnee) {
                    ventesFiltrees.add(v);
                }
            }
        }

        // Agréger les quantités par produit
        Map<Integer, Integer> quantiteParProduit = new HashMap<>();
        for (VenteProduitExtra v : ventesFiltrees) {
            Integer idProduit = v.getIdProduitExtra();
            Integer qte = v.getQuantite() != null ? v.getQuantite() : 0;
            quantiteParProduit.merge(idProduit, qte, Integer::sum);
        }

        // Créer les DTOs
        List<VenteParProduitDto> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : quantiteParProduit.entrySet()) {
            Integer idProduit = entry.getKey();
            Integer quantite = entry.getValue();
            ProduitExtra produit = produitMap.get(idProduit);
            if (produit != null && quantite > 0) {
                result.add(new VenteParProduitDto(
                    idProduit,
                    produit.getNom(),
                    produit.getPrixUnitaire(),
                    quantite
                ));
            }
        }

        // Trier par nom de produit
        result.sort((a, b) -> a.getNomProduit().compareToIgnoreCase(b.getNomProduit()));

        return result;
    }

    public BigDecimal calculerTotalGeneral(List<VenteParProduitDto> ventes) {
        BigDecimal total = BigDecimal.ZERO;
        for (VenteParProduitDto v : ventes) {
            total = total.add(v.getMontantTotal());
        }
        return total;
    }

    public Integer calculerQuantiteTotale(List<VenteParProduitDto> ventes) {
        int total = 0;
        for (VenteParProduitDto v : ventes) {
            total += v.getQuantiteTotale();
        }
        return total;
    }
}
