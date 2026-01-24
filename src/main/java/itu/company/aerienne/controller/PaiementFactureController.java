package itu.company.aerienne.controller;

import itu.company.aerienne.dto.FactureMereDto;
import itu.company.aerienne.dto.PaiementFactureDto;
import itu.company.aerienne.model.PaiementFacture;
import itu.company.aerienne.service.FactureMereService;
import itu.company.aerienne.service.PaiementFactureService;
import itu.company.aerienne.service.SocieteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/paiements-facture")
public class PaiementFactureController {

    @Autowired
    private PaiementFactureService paiementFactureService;

    @Autowired
    private FactureMereService factureMereService;

    @Autowired
    private SocieteService societeService;

    /**
     * Liste tous les paiements de factures
     */
    @GetMapping
    public String listPaiements(Model model) {
        List<PaiementFactureDto> paiements = paiementFactureService.getAllPaiementsWithDetails();
        model.addAttribute("paiements", paiements);
        
        // Calculer le total des paiements
        BigDecimal totalPaiements = BigDecimal.ZERO;
        for (PaiementFactureDto p : paiements) {
            if (p.getMontantPaye() != null) {
                totalPaiements = totalPaiements.add(p.getMontantPaye());
            }
        }
        model.addAttribute("totalPaiements", totalPaiements);
        
        return "paiements/list-facture";
    }

    /**
     * Affiche le formulaire d'ajout d'un paiement
     */
    @GetMapping("/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("paiement", new PaiementFacture());
        model.addAttribute("societes", societeService.findAll());
        model.addAttribute("factures", factureMereService.findAllFacturesNonSoldees());
        return "paiements/form-facture";
    }

    /**
     * API REST pour récupérer les factures non soldées d'une société (pour le JavaScript)
     */
    @GetMapping("/api/factures/{idSociete}")
    @ResponseBody
    public ResponseEntity<List<FactureMereDto>> getFacturesBySociete(@PathVariable Integer idSociete) {
        List<FactureMereDto> factures = factureMereService.getFacturesNonSoldeesDtoBySociete(idSociete);
        return ResponseEntity.ok(factures);
    }

    /**
     * API REST pour récupérer les détails d'une facture
     */
    @GetMapping("/api/facture/{idFacture}")
    @ResponseBody
    public ResponseEntity<FactureMereDto> getFactureDetails(@PathVariable Integer idFacture) {
        return factureMereService.findById(idFacture)
                .map(facture -> ResponseEntity.ok(factureMereService.toDto(facture)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Traite l'ajout d'un nouveau paiement
     */
    @PostMapping("/ajouter")
    public String addPaiement(@RequestParam("idFactureMere") Integer idFactureMere,
                              @RequestParam("datePaiement") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datePaiement,
                              @RequestParam("montantPaye") BigDecimal montantPaye,
                              RedirectAttributes redirectAttributes) {
        try {
            // Vérifier que le montant ne dépasse pas le restant
            var facture = factureMereService.findById(idFactureMere).orElse(null);
            if (facture != null) {
                BigDecimal restant = facture.getMontantRestant();
                if (montantPaye.compareTo(restant) > 0) {
                    redirectAttributes.addFlashAttribute("errorMessage", 
                        "Le montant du paiement (" + montantPaye + " Ar) dépasse le montant restant (" + restant + " Ar)!");
                    return "redirect:/paiements-facture/ajouter";
                }
            }

            PaiementFacture paiement = new PaiementFacture();
            paiement.setIdFactureMere(idFactureMere);
            paiement.setDatePaiement(datePaiement);
            paiement.setMontantPaye(montantPaye);
            
            paiementFactureService.save(paiement);
            redirectAttributes.addFlashAttribute("successMessage", "Paiement ajouté avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'ajout: " + e.getMessage());
        }
        return "redirect:/paiements-facture";
    }

    /**
     * Supprime un paiement
     */
    @GetMapping("/supprimer/{id}")
    public String deletePaiement(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            paiementFactureService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Paiement supprimé avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression: " + e.getMessage());
        }
        return "redirect:/paiements-facture";
    }
}
