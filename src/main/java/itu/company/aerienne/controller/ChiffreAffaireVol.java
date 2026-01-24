package itu.company.aerienne.controller;

import itu.company.aerienne.dto.ChiffreAffaireVolDto;
import itu.company.aerienne.service.ChiffreAffaireVolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        BigDecimal totalMontantTotal = BigDecimal.ZERO;
        BigDecimal totalMontantAPayer = BigDecimal.ZERO;
        BigDecimal totalMontantResteAPayer = BigDecimal.ZERO;

        for (ChiffreAffaireVolDto dto : chiffreAffairesVols) {
            totalMontantTicketVendu = totalMontantTicketVendu.add(dto.getMontantTicketVendu() != null ? dto.getMontantTicketVendu() : BigDecimal.ZERO);
            totalMontantPublicite = totalMontantPublicite.add(dto.getMontantPublicite() != null ? dto.getMontantPublicite() : BigDecimal.ZERO);
            totalMontantTotal = totalMontantTotal.add(dto.getMontantTotal() != null ? dto.getMontantTotal() : BigDecimal.ZERO);
            totalMontantAPayer = totalMontantAPayer.add(dto.getMontantAPayer() != null ? dto.getMontantAPayer() : BigDecimal.ZERO);
            totalMontantResteAPayer = totalMontantResteAPayer.add(dto.getMontantResteAPayer() != null ? dto.getMontantResteAPayer() : BigDecimal.ZERO);
        }

        model.addAttribute("totalMontantTicketVendu", totalMontantTicketVendu);
        model.addAttribute("totalMontantPublicite", totalMontantPublicite);
        model.addAttribute("totalMontantTotal", totalMontantTotal);
        model.addAttribute("totalMontantAPayer", totalMontantAPayer);
        model.addAttribute("totalMontantResteAPayer", totalMontantResteAPayer);

        return "chiffre-affaires/chiffre-affaire-vol";
    }
}
