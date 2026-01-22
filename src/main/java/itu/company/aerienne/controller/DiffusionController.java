package itu.company.aerienne.controller;

import itu.company.aerienne.dto.DiffusionChiffreAffaireDto;
import itu.company.aerienne.dto.DiffusionFormDto;
import itu.company.aerienne.service.DiffusionPublicitaireService;
import itu.company.aerienne.service.SocieteService;
import itu.company.aerienne.service.VolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class DiffusionController {

    @Autowired
    private SocieteService societeService;
    
    @Autowired
    private VolService volService;
    
    @Autowired
    private DiffusionPublicitaireService diffusionService;

    @GetMapping("/ajouter-diffusion")
    public String showForm(Model model) {
        model.addAttribute("societes", societeService.findAll());
        model.addAttribute("vols", volService.getAllVolDetails());
        model.addAttribute("diffusion", new DiffusionFormDto());
        return "diffusion/ajout-diffusion";
    }

    @PostMapping("/diffusions/ajouter")
    public String ajouterDiffusion(@ModelAttribute("diffusion") DiffusionFormDto diffusionForm, 
                                    RedirectAttributes redirectAttributes) {
        try {
            diffusionService.ajouterDiffusion(diffusionForm);
            redirectAttributes.addFlashAttribute("successMessage", "Diffusion ajoutée avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout: " + e.getMessage());
        }
        return "redirect:/ajouter-diffusion";
    }

    @GetMapping("/chiffre-affaire-diffusion")
    public String showChiffreAffaireDiffusion(
            @RequestParam(value = "mois", required = false) Integer mois,
            @RequestParam(value = "annee", required = false) Integer annee,
            Model model) {

        if (mois == null) {
            mois = 12;
        }
        if (annee == null) {
            annee = 2025;
        }

        model.addAttribute("moisRecherche", mois);
        model.addAttribute("anneeRecherche", annee);

        // Récupérer les données
        List<DiffusionChiffreAffaireDto> diffusions = diffusionService.getChiffreAffaireDiffusion(mois, annee);
        model.addAttribute("diffusions", diffusions);

        // Calculer les totaux
        BigDecimal totalChiffreAffaire = BigDecimal.ZERO;
        BigDecimal totalMontantPaye = BigDecimal.ZERO;
        BigDecimal totalResteAPayer = BigDecimal.ZERO;
        int totalNombreDiffusion = 0;
        
        for (DiffusionChiffreAffaireDto dto : diffusions) {
            totalChiffreAffaire = totalChiffreAffaire.add(dto.getTotalCout() != null ? dto.getTotalCout() : BigDecimal.ZERO);
            totalMontantPaye = totalMontantPaye.add(dto.getMontantPaye() != null ? dto.getMontantPaye() : BigDecimal.ZERO);
            totalResteAPayer = totalResteAPayer.add(dto.getResteAPayer() != null ? dto.getResteAPayer() : BigDecimal.ZERO);
            totalNombreDiffusion += dto.getNombreDiffusion() != null ? dto.getNombreDiffusion() : 0;
        }
        
        model.addAttribute("totalChiffreAffaire", totalChiffreAffaire);
        model.addAttribute("totalNombreDiffusion", totalNombreDiffusion);
        model.addAttribute("totalMontantPaye", totalMontantPaye);
        model.addAttribute("totalResteAPayer", totalResteAPayer);

        // Coût unitaire pour affichage
        model.addAttribute("coutUnitaire", diffusionService.getCoutUnitaire());

        return "chiffre-affaires/chiffre-affaire-diffusion";
    }
}
