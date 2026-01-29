package itu.company.aerienne.controller;

import itu.company.aerienne.dto.ChiffreAffaireVolDto;
import itu.company.aerienne.service.ChiffreAffaireVolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/chiffre-affaire-vol")
public class ChiffreAffaireVol {

    @Autowired
    private ChiffreAffaireVolService chiffreAffaireVolService;

    @GetMapping
    public String CA(Model model) {
        List<ChiffreAffaireVolDto> chiffreAffairesVols = chiffreAffaireVolService.getChiffreAffaireVols();
        model.addAttribute("chiffreAffairesVols", chiffreAffairesVols);

        // Calculer les totaux
        BigDecimal totalMontantTicketVendu = BigDecimal.ZERO;
        BigDecimal totalMontantPublicite = BigDecimal.ZERO;
        BigDecimal totalMontantProduitExtra = BigDecimal.ZERO;
        BigDecimal totalMontantTotal = BigDecimal.ZERO;
        BigDecimal totalMontantAPayer = BigDecimal.ZERO;
        BigDecimal totalMontantResteAPayer = BigDecimal.ZERO;

        for (ChiffreAffaireVolDto dto : chiffreAffairesVols) {
            totalMontantTicketVendu = totalMontantTicketVendu.add(dto.getMontantTicketVendu() != null ? dto.getMontantTicketVendu() : BigDecimal.ZERO);
            totalMontantPublicite = totalMontantPublicite.add(dto.getMontantPublicite() != null ? dto.getMontantPublicite() : BigDecimal.ZERO);
            totalMontantProduitExtra = totalMontantProduitExtra.add(dto.getMontantProduitExtra() != null ? dto.getMontantProduitExtra() : BigDecimal.ZERO);
            totalMontantTotal = totalMontantTotal.add(dto.getMontantTotal() != null ? dto.getMontantTotal() : BigDecimal.ZERO);
            totalMontantAPayer = totalMontantAPayer.add(dto.getMontantAPayer() != null ? dto.getMontantAPayer() : BigDecimal.ZERO);
            totalMontantResteAPayer = totalMontantResteAPayer.add(dto.getMontantResteAPayer() != null ? dto.getMontantResteAPayer() : BigDecimal.ZERO);
        }

        model.addAttribute("totalMontantTicketVendu", totalMontantTicketVendu);
        model.addAttribute("totalMontantPublicite", totalMontantPublicite);
        model.addAttribute("totalMontantProduitExtra", totalMontantProduitExtra);
        model.addAttribute("totalMontantTotal", totalMontantTotal);
        model.addAttribute("totalMontantAPayer", totalMontantAPayer);
        model.addAttribute("totalMontantResteAPayer", totalMontantResteAPayer);

        return "chiffre-affaires/chiffre-affaire-vol";
    }

    // Affichage filtré par mois et année
    @GetMapping(params = {"mois", "annee"})
    public String CAFiltre(@RequestParam("mois") int mois,
                           @RequestParam("annee") int annee,
                           Model model) {
        List<ChiffreAffaireVolDto> chiffreAffairesVols = chiffreAffaireVolService.getChiffreAffaireVols();

        // Filtrer selon la date de départ (mois/année)
        List<ChiffreAffaireVolDto> filtered = chiffreAffairesVols.stream()
                .filter(dto -> dto.getVol() != null
                        && dto.getVol().getDateHeureDepart() != null
                        && dto.getVol().getDateHeureDepart().getMonthValue() == mois
                        && dto.getVol().getDateHeureDepart().getYear() == annee)
                .toList();

        model.addAttribute("chiffreAffairesVols", filtered);

        // Calculer les totaux sur la période filtrée
        BigDecimal totalMontantTicketVendu = BigDecimal.ZERO;
        BigDecimal totalMontantPublicite = BigDecimal.ZERO;
        BigDecimal totalMontantProduitExtra = BigDecimal.ZERO;
        BigDecimal totalMontantTotal = BigDecimal.ZERO;
        BigDecimal totalMontantAPayer = BigDecimal.ZERO;
        BigDecimal totalMontantResteAPayer = BigDecimal.ZERO;

        for (ChiffreAffaireVolDto dto : filtered) {
            totalMontantTicketVendu = totalMontantTicketVendu.add(dto.getMontantTicketVendu() != null ? dto.getMontantTicketVendu() : BigDecimal.ZERO);
            totalMontantPublicite = totalMontantPublicite.add(dto.getMontantPublicite() != null ? dto.getMontantPublicite() : BigDecimal.ZERO);
            totalMontantProduitExtra = totalMontantProduitExtra.add(dto.getMontantProduitExtra() != null ? dto.getMontantProduitExtra() : BigDecimal.ZERO);
            totalMontantTotal = totalMontantTotal.add(dto.getMontantTotal() != null ? dto.getMontantTotal() : BigDecimal.ZERO);
            totalMontantAPayer = totalMontantAPayer.add(dto.getMontantAPayer() != null ? dto.getMontantAPayer() : BigDecimal.ZERO);
            totalMontantResteAPayer = totalMontantResteAPayer.add(dto.getMontantResteAPayer() != null ? dto.getMontantResteAPayer() : BigDecimal.ZERO);
        }

        model.addAttribute("totalMontantTicketVendu", totalMontantTicketVendu);
        model.addAttribute("totalMontantPublicite", totalMontantPublicite);
        model.addAttribute("totalMontantProduitExtra", totalMontantProduitExtra);
        model.addAttribute("totalMontantTotal", totalMontantTotal);
        model.addAttribute("totalMontantAPayer", totalMontantAPayer);
        model.addAttribute("totalMontantResteAPayer", totalMontantResteAPayer);

        // Pour l'affichage de la période sélectionnée dans la vue
        model.addAttribute("moisRecherche", mois);
        model.addAttribute("anneeRecherche", annee);

        return "chiffre-affaires/chiffre-affaire-vol";
    }
}
