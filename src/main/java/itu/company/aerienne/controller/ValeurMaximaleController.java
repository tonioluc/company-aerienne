package itu.company.aerienne.controller;

import itu.company.aerienne.dto.ValeurMaxParVolParAvionDto;
import itu.company.aerienne.service.AvionService;
import itu.company.aerienne.service.ValeurMaximaleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/valeur-maximale")
public class ValeurMaximaleController {

    @Autowired
    private ValeurMaximaleService valeurMaximaleService;

    @Autowired
    private AvionService avionService;

    /**
     * Affiche le formulaire de sélection d'avion.
     * Si un avion est sélectionné (via paramètre idAvion), affiche les détails.
     */
    @GetMapping
    public String showForm(@RequestParam(value = "idAvion", required = false) Integer idAvion, Model model) {
        // Toujours charger la liste des avions pour le select
        model.addAttribute("avions", avionService.findAll());

        // Si un avion est sélectionné, calculer et afficher les valeurs maximales
        if (idAvion != null) {
            ValeurMaxParVolParAvionDto result = valeurMaximaleService.getValeurMaximaleParAvion(idAvion);
            model.addAttribute("valeurMax", result);
            model.addAttribute("selectedAvionId", idAvion);
        }

        return "avions/valeur-maximale-avion";
    }

    /**
     * Traite le formulaire de sélection d'avion (POST -> redirect GET avec paramètre).
     */
    @PostMapping
    public String handleFormSubmit(@RequestParam("idAvion") Integer idAvion) {
        return "redirect:/valeur-maximale?idAvion=" + idAvion;
    }
}
