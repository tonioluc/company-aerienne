package itu.company.aerienne.controller;

import itu.company.aerienne.dto.FactureDiffusionDto;
import itu.company.aerienne.dto.FactureMereDto;
import itu.company.aerienne.service.FactureDiffusionService;
import itu.company.aerienne.service.FactureMereService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/factures")
public class FactureController {

    @Autowired
    private FactureMereService factureMereService;

    @Autowired
    private FactureDiffusionService factureDiffusionService;

    /**
     * Liste toutes les factures mères
     */
    @GetMapping
    public String listFactures(Model model) {
        List<FactureMereDto> factures = factureMereService.getAllFacturesDto();
        model.addAttribute("factures", factures);

        // Calculer les totaux
        BigDecimal totalMontant = BigDecimal.ZERO;
        BigDecimal totalPaye = BigDecimal.ZERO;
        BigDecimal totalRestant = BigDecimal.ZERO;

        for (FactureMereDto f : factures) {
            totalMontant = totalMontant.add(f.getMontantTotal() != null ? f.getMontantTotal() : BigDecimal.ZERO);
            totalPaye = totalPaye.add(f.getMontantPaye() != null ? f.getMontantPaye() : BigDecimal.ZERO);
            totalRestant = totalRestant.add(f.getMontantRestant() != null ? f.getMontantRestant() : BigDecimal.ZERO);
        }

        model.addAttribute("totalMontant", totalMontant);
        model.addAttribute("totalPaye", totalPaye);
        model.addAttribute("totalRestant", totalRestant);

        return "factures/list";
    }

    /**
     * Affiche les détails d'une facture mère (avec ses factures diffusion)
     */
    @GetMapping("/{id}")
    public String detailFacture(@PathVariable("id") Integer id, Model model) {
        // Récupérer la facture mère
        var factureMere = factureMereService.findById(id).orElse(null);
        if (factureMere == null) {
            return "redirect:/factures";
        }

        FactureMereDto factureMereDto = factureMereService.toDto(factureMere);
        model.addAttribute("factureMere", factureMereDto);

        // Récupérer les factures diffusion liées
        List<FactureDiffusionDto> facturesDiffusion = factureDiffusionService.getFactureDiffusionDetails(id);
        model.addAttribute("facturesDiffusion", facturesDiffusion);

        // Calculer le total des diffusions
        BigDecimal totalDiffusions = BigDecimal.ZERO;
        int totalNombreDiffusion = 0;
        for (FactureDiffusionDto fd : facturesDiffusion) {
            totalDiffusions = totalDiffusions.add(fd.getMontant() != null ? fd.getMontant() : BigDecimal.ZERO);
            totalNombreDiffusion += fd.getNombreDiffusion() != null ? fd.getNombreDiffusion() : 0;
        }
        model.addAttribute("totalDiffusions", totalDiffusions);
        model.addAttribute("totalNombreDiffusion", totalNombreDiffusion);

        return "factures/detail";
    }
}
