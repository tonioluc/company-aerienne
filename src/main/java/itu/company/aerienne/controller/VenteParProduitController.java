package itu.company.aerienne.controller;

import itu.company.aerienne.dto.VenteParProduitDto;
import itu.company.aerienne.service.VenteParProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class VenteParProduitController {

    @Autowired
    private VenteParProduitService venteParProduitService;

    @GetMapping("/ventes-par-produit")
    public String afficherVentesParProduit(
            @RequestParam(required = false) Integer mois,
            @RequestParam(required = false) Integer annee,
            Model model) {

        // Si pas de filtre, utiliser le mois et l'année courants
        if (mois == null && annee == null) {
            LocalDate now = LocalDate.now();
            mois = now.getMonthValue();
            annee = now.getYear();
        }

        List<VenteParProduitDto> ventes = venteParProduitService.getVentesParProduit(mois, annee);
        BigDecimal totalGeneral = venteParProduitService.calculerTotalGeneral(ventes);
        Integer quantiteTotale = venteParProduitService.calculerQuantiteTotale(ventes);

        model.addAttribute("ventes", ventes);
        model.addAttribute("totalGeneral", totalGeneral);
        model.addAttribute("quantiteTotale", quantiteTotale);
        model.addAttribute("mois", mois);
        model.addAttribute("annee", annee);

        return "ventes/ventes-par-produit";
    }
}
