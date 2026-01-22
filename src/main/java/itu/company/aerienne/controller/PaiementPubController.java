package itu.company.aerienne.controller;

import itu.company.aerienne.dto.PaiementPubDto;
import itu.company.aerienne.model.PaiementPub;
import itu.company.aerienne.service.PaiementPubService;
import itu.company.aerienne.service.SocieteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/paiements-pub")
public class PaiementPubController {

    @Autowired
    private PaiementPubService paiementPubService;

    @Autowired
    private SocieteService societeService;

    /**
     * Liste tous les paiements publicitaires.
     */
    @GetMapping
    public String listPaiements(Model model) {
        List<PaiementPubDto> paiements = paiementPubService.getAllPaiementsWithDetails();
        model.addAttribute("paiements", paiements);
        
        // Calculer le total des paiements
        BigDecimal totalPaiements = BigDecimal.ZERO;
        for (PaiementPubDto p : paiements) {
            if (p.getMontant() != null) {
                totalPaiements = totalPaiements.add(p.getMontant());
            }
        }
        model.addAttribute("totalPaiements", totalPaiements);
        
        return "paiements/list";
    }

    /**
     * Affiche le formulaire d'ajout d'un paiement.
     */
    @GetMapping("/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("paiement", new PaiementPub());
        model.addAttribute("societes", societeService.findAll());
        return "paiements/form";
    }

    /**
     * Traite l'ajout d'un nouveau paiement.
     */
    @PostMapping("/ajouter")
    public String addPaiement(@RequestParam("idSociete") Integer idSociete,
                              @RequestParam("datePaiement") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datePaiement,
                              @RequestParam("montant") BigDecimal montant,
                              RedirectAttributes redirectAttributes) {
        try {
            PaiementPub paiement = new PaiementPub();
            paiement.setIdSociete(idSociete);
            paiement.setDatePaiement(datePaiement);
            paiement.setMontant(montant);
            
            paiementPubService.save(paiement);
            redirectAttributes.addFlashAttribute("successMessage", "Paiement ajouté avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout: " + e.getMessage());
        }
        return "redirect:/paiements-pub";
    }

    /**
     * Affiche le formulaire de modification d'un paiement.
     */
    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        PaiementPub paiement = paiementPubService.findById(id).orElse(null);
        if (paiement == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Paiement non trouvé!");
            return "redirect:/paiements-pub";
        }
        model.addAttribute("paiement", paiement);
        model.addAttribute("societes", societeService.findAll());
        return "paiements/form";
    }

    /**
     * Traite la modification d'un paiement.
     */
    @PostMapping("/modifier/{id}")
    public String updatePaiement(@PathVariable("id") Integer id,
                                  @RequestParam("idSociete") Integer idSociete,
                                  @RequestParam("datePaiement") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datePaiement,
                                  @RequestParam("montant") BigDecimal montant,
                                  RedirectAttributes redirectAttributes) {
        try {
            PaiementPub paiement = paiementPubService.findById(id).orElse(null);
            if (paiement == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Paiement non trouvé!");
                return "redirect:/paiements-pub";
            }
            
            paiement.setIdSociete(idSociete);
            paiement.setDatePaiement(datePaiement);
            paiement.setMontant(montant);
            
            paiementPubService.save(paiement);
            redirectAttributes.addFlashAttribute("successMessage", "Paiement modifié avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la modification: " + e.getMessage());
        }
        return "redirect:/paiements-pub";
    }

    /**
     * Supprime un paiement.
     */
    @GetMapping("/supprimer/{id}")
    public String deletePaiement(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            paiementPubService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Paiement supprimé avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression: " + e.getMessage());
        }
        return "redirect:/paiements-pub";
    }
}
